#!/usr/bin/env node
// Strips modern OpenType and vertical layout tables (GDEF, GPOS, GSUB, vhea, vmtx, gasp)
// from assets/cn.ttf, leaving only standard TrueType 1.0 tables compatible with
// the legacy FreeType 2.4.2 engine on Android 2.3.7 (Sony PMCA 1st-gen cameras like A6000).

import { readFileSync, writeFileSync } from 'node:fs';
import { dirname, join, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const FONT = join(ROOT, 'assets', 'cn.ttf');

function calcChecksum(buf) {
  let sum = 0;
  const n = Math.ceil(buf.length / 4);
  for (let i = 0; i < n; i++) {
    const o = i * 4;
    const v = (buf[o] << 24) | ((buf[o + 1] || 0) << 16) | ((buf[o + 2] || 0) << 8) | (buf[o + 3] || 0);
    sum = (sum + (v >>> 0)) >>> 0;
  }
  return sum;
}

export function sanitizeFont(file = FONT) {
  const orig = readFileSync(file);
  const numTablesOrig = orig.readUInt16BE(4);
  const keepTags = new Set([
    'OS/2', 'cmap', 'cvt ', 'fpgm', 'glyf', 'head', 'hhea', 'hmtx', 'loca', 'maxp', 'name', 'post', 'prep'
  ]);

  const tables = [];
  const removed = [];
  for (let i = 0; i < numTablesOrig; i++) {
    const o = 12 + i * 16;
    const tag = orig.toString('ascii', o, o + 4);
    const offset = orig.readUInt32BE(o + 8);
    const length = orig.readUInt32BE(o + 12);
    if (keepTags.has(tag)) {
      const data = Buffer.from(orig.subarray(offset, offset + length));
      tables.push({ tag, data });
    } else {
      removed.push(tag);
    }
  }

  if (removed.length === 0) {
    console.log(`sanitize-font: ${file} already has clean TrueType 1.0 tables`);
    return;
  }

  // Sort alphabetically by tag as required by OpenType/TrueType spec
  tables.sort((a, b) => a.tag.localeCompare(b.tag));

  const numTables = tables.length;
  let maxPow2 = 1;
  while (maxPow2 * 2 <= numTables) maxPow2 *= 2;
  const searchRange = maxPow2 * 16;
  const entrySelector = Math.log2(maxPow2);
  const rangeShift = numTables * 16 - searchRange;

  const header = Buffer.alloc(12);
  header.writeUInt32BE(0x00010000, 0); // sfntVersion: TrueType 1.0
  header.writeUInt16BE(numTables, 4);
  header.writeUInt16BE(searchRange, 6);
  header.writeUInt16BE(entrySelector, 8);
  header.writeUInt16BE(rangeShift, 10);

  const dir = Buffer.alloc(numTables * 16);
  let currentOffset = 12 + numTables * 16;
  const outBlocks = [header, dir];
  let headOffset = -1;

  for (let i = 0; i < numTables; i++) {
    const t = tables[i];
    if (t.tag === 'head') headOffset = currentOffset;
    const dirO = i * 16;
    dir.write(t.tag, dirO, 4, 'ascii');
    const sum = calcChecksum(t.data);
    dir.writeUInt32BE(sum, dirO + 4);
    dir.writeUInt32BE(currentOffset, dirO + 8);
    dir.writeUInt32BE(t.data.length, dirO + 12);

    outBlocks.push(t.data);
    currentOffset += t.data.length;
    const pad = (4 - (t.data.length % 4)) % 4;
    if (pad > 0) {
      outBlocks.push(Buffer.alloc(pad));
      currentOffset += pad;
    }
  }

  const result = Buffer.concat(outBlocks);

  // Recalculate head checkSumAdjustment
  result.writeUInt32BE(0, headOffset + 8);
  const fontSum = calcChecksum(result);
  const checkSumAdjustment = (0xB1B0AFBA - fontSum) >>> 0;
  result.writeUInt32BE(checkSumAdjustment, headOffset + 8);

  // Recompute head table checksum in directory
  const headDirIdx = tables.findIndex((t) => t.tag === 'head');
  const headTableData = result.subarray(headOffset, headOffset + tables[headDirIdx].data.length);
  const newHeadSum = calcChecksum(headTableData);
  result.writeUInt32BE(newHeadSum, 12 + headDirIdx * 16 + 4);

  writeFileSync(file, result);
  console.log(`sanitize-font: stripped [${removed.join(', ')}], new size: ${result.length} bytes`);
}

if (process.argv[1] && fileURLToPath(import.meta.url) === resolve(process.argv[1])) {
  sanitizeFont();
}

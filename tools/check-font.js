#!/usr/bin/env node
// Chinese text is drawn with the font bundled in assets/cn.ttf (see src/com/voxivoid/recipelab/Cn.java), and that
// font is a subset: it carries exactly the characters the sources used on the day it was made. A new string whose
// characters are not in it still compiles and still runs — it just draws 口 on the camera. This is the check for
// that, next to tools/check-version.sh.
//
//   node tools/check-font.js         every character used in src/ and res/ must be in the font; exit 1 if not
//   node tools/check-font.js --text  print every character the font must carry — the CJK used in src/ and res/ plus
//                                    printable ASCII, so Latin and digits come from the font too — one string, for
//                                    pyftsubset's --text-file
//
// Regenerating the subset (needs fontTools: pip install fonttools, and DroidSansFallbackFull.ttf from AOSP — see
// docs/I18N.md for the URL):
//
//   node tools/check-font.js --text > out/cn-chars.txt
//   pyftsubset DroidSansFallbackFull.ttf --text-file=out/cn-chars.txt --output-file=assets/cn.ttf
//
// Node only, no dependencies.
import { readFileSync, readdirSync } from 'node:fs';
import { dirname, join, relative, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const FONT = join(ROOT, 'assets', 'cn.ttf');
const SOURCES = ['src', 'res'];
const SKIP = new Set(['.git', 'out', 'dist', 'node_modules']);
const EXTENSIONS = /\.(java|xml)$/;

/** printable ASCII: carried by the font too, so Latin and digits never depend on the camera's fallback face */
const ASCII = Array.from({ length: 0x7e - 0x20 + 1 }, (_, i) => 0x20 + i);

/** the CJK scripts and fullwidth punctuation range — what a subset font is for */
const isCjk = (cp) =>
  (cp >= 0x2e80 && cp <= 0x2eff) || (cp >= 0x3000 && cp <= 0x303f) || (cp >= 0x3400 && cp <= 0x4dbf) ||
  (cp >= 0x4e00 && cp <= 0x9fff) || (cp >= 0xf900 && cp <= 0xfaff) || (cp >= 0xfe30 && cp <= 0xfe4f) ||
  (cp >= 0xff00 && cp <= 0xffef);

const fail = (message) => {
  console.error(`check-font: ${message}`);
  process.exit(1);
};

/** every code point the font has a glyph for, read straight out of its cmap */
function glyphs(file) {
  const ttf = readFileSync(file);
  const tables = ttf.readUInt16BE(4);
  let cmap = 0;
  for (let i = 0; i < tables; i++) {
    const o = 12 + i * 16;
    if (ttf.toString('ascii', o, o + 4) === 'cmap') cmap = ttf.readUInt32BE(o + 8);
  }
  if (!cmap) fail(`${relative(ROOT, file)} has no cmap table — that is not a font`);

  // the Unicode BMP subtable if there is one, else the last one that is not (3,0)/(1,0) symbol-encoded
  let sub = 0;
  for (let i = 0; i < ttf.readUInt16BE(cmap + 2); i++) {
    const o = cmap + 4 + i * 8;
    const pid = ttf.readUInt16BE(o), eid = ttf.readUInt16BE(o + 2);
    if ((pid === 3 && (eid === 1 || eid === 10)) || pid === 0) sub = cmap + ttf.readUInt32BE(o + 4);
  }
  if (!sub) fail(`${relative(ROOT, file)} has no Unicode cmap subtable`);

  const have = new Set();
  const format = ttf.readUInt16BE(sub);
  if (format === 4) {
    const segX2 = ttf.readUInt16BE(sub + 6), segments = segX2 / 2;
    const endO = sub + 14, startO = endO + segX2 + 2, deltaO = startO + segX2, rangeO = deltaO + segX2;
    for (let s = 0; s < segments; s++) {
      const end = ttf.readUInt16BE(endO + s * 2), start = ttf.readUInt16BE(startO + s * 2);
      const delta = ttf.readInt16BE(deltaO + s * 2), range = ttf.readUInt16BE(rangeO + s * 2);
      for (let cp = start; cp <= end && cp !== 0xffff; cp++) {
        const gid = range === 0 ? (cp + delta) & 0xffff : ttf.readUInt16BE(rangeO + s * 2 + range + (cp - start) * 2);
        if (gid !== 0) have.add(cp);
      }
    }
  } else if (format === 12) {
    const groups = ttf.readUInt32BE(sub + 12);
    for (let g = 0; g < groups; g++) {
      const o = sub + 16 + g * 12;
      const start = ttf.readUInt32BE(o), end = ttf.readUInt32BE(o + 4), gid = ttf.readUInt32BE(o + 8);
      for (let cp = start; cp <= end; cp++) if (gid + (cp - start) !== 0) have.add(cp);
    }
  } else {
    fail(`${relative(ROOT, file)}: cmap format ${format} is not supported — re-export the subset as format 4/12`);
  }
  return have;
}

/**
 * The characters the app can actually put on screen: comments are stripped first, because a comment is never
 * drawn and demanding a glyph for the Chinese in a JavaDoc would only push the wording around for nothing.
 */
function drawable(text, file) {
  return file.endsWith('.java')
    ? text.replace(/\/\*[\s\S]*?\*\//g, ' ').replace(/\/\/[^\n]*/g, ' ')
    : text.replace(/<!--[\s\S]*?-->/g, ' ');
}

function used(file, into) {
  const name = relative(ROOT, file).replaceAll('\\', '/');
  for (const ch of drawable(readFileSync(file, 'utf8'), file)) {
    const cp = ch.codePointAt(0);
    if (!isCjk(cp)) continue;
    if (!into.has(cp)) into.set(cp, new Set());
    into.get(cp).add(name);
  }
  return into;
}

function walk(dir, into) {
  for (const entry of readdirSync(dir, { withFileTypes: true })) {
    if (SKIP.has(entry.name)) continue;
    const full = join(dir, entry.name);
    if (entry.isDirectory()) walk(full, into);
    else if (EXTENSIONS.test(entry.name)) used(full, into);
  }
  return into;
}

const chars = SOURCES.reduce((into, dir) => walk(join(ROOT, dir), into), new Map());

if (process.argv.includes('--text')) {
  const all = [...new Set([...ASCII, ...chars.keys()])].sort((a, b) => a - b);
  process.stdout.write(all.map((cp) => String.fromCodePoint(cp)).join(''));
  process.exit(0);
}

const have = glyphs(FONT);
const missing = [...chars.keys()].filter((cp) => !have.has(cp)).sort((a, b) => a - b);
const missingAscii = ASCII.filter((cp) => !have.has(cp));

if (missing.length || missingAscii.length) {
  console.error(`check-font: assets/cn.ttf does not cover what ${SOURCES.join('/')} draws`);
  for (const cp of missing) {
    const where = [...chars.get(cp)].join(', ');
    console.error(`  ${String.fromCodePoint(cp)}  U+${cp.toString(16).toUpperCase().padStart(4, '0')}  ${where}`);
  }
  if (missingAscii.length) console.error(`  ${missingAscii.length} printable ASCII characters are missing too — Latin and digits must come from the font, not from whatever the firmware falls back to`);
  console.error(`\n${missing.length} of ${chars.size} characters used in the app are missing. Regenerate the subset, then build:`);
  console.error('  node tools/check-font.js --text > out/cn-chars.txt');
  console.error('  pyftsubset DroidSansFallbackFull.ttf --text-file=out/cn-chars.txt --output-file=assets/cn.ttf');
  process.exit(1);
}

console.log(`check-font: ok — all ${chars.size} characters used in ${SOURCES.join('/')} and printable ASCII are in assets/cn.ttf (${have.size} glyphs)`);

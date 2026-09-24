#!/usr/bin/env bash
# Sync upstream changes from voxivoid/recipe-lab-sony-pmca into this fork
# Usage:
#   ./tools/sync-upstream.sh          # merge upstream/main (recommended)
#   ./tools/sync-upstream.sh --rebase # rebase onto upstream/main
set -euo pipefail
cd "$(dirname "$0")/.."

UPSTREAM_URL="https://github.com/voxivoid/recipe-lab-sony-pmca.git"

echo "=== [1/4] 检查 Git 状态与 Upstream 配置 ==="
if [ -n "$(git status --porcelain)" ]; then
  echo "错误：当前工作区有未提交的代码，请先 commit 或 stash 后再同步。" >&2
  exit 1
fi

if ! git remote get-url upstream &>/dev/null; then
  echo "未检测到 upstream remote，正在自动添加: $UPSTREAM_URL"
  git remote add upstream "$UPSTREAM_URL"
fi

echo "=== [2/4] 获取上游最新代码 (git fetch upstream) ==="
git fetch upstream main --tags

MODE="merge"
if [ "${1:-}" = "--rebase" ]; then
  MODE="rebase"
fi

if [ "$MODE" = "rebase" ]; then
  echo "=== [3/4] 正在 Rebase 到 upstream/main ==="
  git rebase upstream/main
else
  echo "=== [3/4] 正在合并 upstream/main (git merge) ==="
  git merge upstream/main -m "chore: sync upstream changes"
fi

echo "=== [4/4] 运行单元测试验证代码一致性 ==="
./tools/test.sh

echo ""
echo "=========================================================="
echo " 同步与测试全部成功！"
echo " 您可以随时运行 'git push origin main' 推送到您的 Fork 仓库。"
echo "=========================================================="

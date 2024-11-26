# fail the whole script if any command bellow fails
set -e

log() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') --- $1"
}
log "Executing Linux build script..."

script_path=$(realpath "$0")
log "Script path: $script_path"

rm -rf dist
rm -rf node_modules

mkdir dist

npm install

# check package.json for more details
log "Transpiling the ABAP code..."
npm run transpile

# disable abaplint
# npm run lint

# Find all .mjs files in the current directory and its subdirectories,
# and replaces all occurrences of %23 with # in those files.
find . -name '*.mjs' -print0 | xargs -0 sed -i '' 's/%23/#/g'

log "Packaging the code..."
esbuild src/run.mjs --tsconfig=./tsconfig.json --bundle --outdir=dist --format=esm --target=es2022 \
    --external:tls --external:net --external:util --external:crypto --external:zlib \
    --external:http --external:https --external:fs --external:path --external:url \
    --external:sdk \
    --inject:./src/lib/polyfills/buffer.js --inject:./src/lib/polyfills/process.js \
    --out-extension:.js=.mjs

log "Linux build script $script_path completed!"

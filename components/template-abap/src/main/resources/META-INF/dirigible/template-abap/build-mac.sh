# fail the whole script if any command bellow fails
set -e

rm -rf dist
rm -rf node_modules

mkdir dist

npm install

# check package.json for more details
npm run transpile

npm run lint

# Find all .mjs files in the current directory and its subdirectories,
# and replaces all occurrences of %23 with # in those files.
find . -name '*.mjs' -print0 | xargs -0 sed -i '' 's/%23/#/g'

esbuild src/run.mjs --tsconfig=./tsconfig.json --bundle --outdir=dist --format=esm --target=es2022 \
    --external:tls --external:net --external:util --external:crypto --external:zlib \
    --external:http --external:https --external:fs --external:path --external:url \
    --external:sdk \
    --inject:./src/lib/polyfills/buffer.js --inject:./src/lib/polyfills/process.js \
    --out-extension:.js=.mjs
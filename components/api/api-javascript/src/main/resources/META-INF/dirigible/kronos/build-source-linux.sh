# fail the whole script if any command bellow fails
set -e

# clean
rm -rf ./dist
rm -rf ./node_modules

# install node modules
npm install

mkdir dist

npm install

npm run transpile

npm run lint

# Find all .mjs files in the current directory and its subdirectories,
# and replaces all occurrences of %23 with # in those files.
find . -name '*.mjs' -print | xargs sed -i 's/%23/#/g'

# build esm
esbuild $(find ./src/js -iname '*.ts' -not -iname '*.d.ts') '--out-extension:.js=.mjs' --sourcemap=inline --outdir=dist/esm --format=esm --target=es2022

# build cjs
esbuild $(find ./src/js -iname '*.ts' -not -iname '*.d.ts') --sourcemap=inline --outdir=dist/cjs --format=cjs --target=es2022

# build dts
tsc --emitDeclarationOnly --outDir dist/dts

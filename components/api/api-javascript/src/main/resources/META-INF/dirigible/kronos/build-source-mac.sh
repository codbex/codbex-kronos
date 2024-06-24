# fail the whole script if any command bellow fails
set -e

echo 'Building API sources...'

# clean
echo 'Cleaning folders...'

rm -rf ./dist
rm -rf ./node_modules

# install node modules
mkdir dist

npm install

# Check whether the ABAP API can be transpiled correctly
echo 'Transpiling the code...'
npm run transpile

echo 'Executing lint...'
npm run lint

# Find all .mjs files in the current directory and its subdirectories,
# and replaces all occurrences of %23 with # in those files.
echo 'Replacing...'
find . -name '*.mjs' -print0 | xargs -0 sed -i '' 's/%23/#/g'

# Remove transpiled ABAP lib since it is not needed.
# It will be transpiled by the ABAP projects
rm -rf dist/abap

# build esm
echo 'Building esm files...'
esbuild $(find ./src/js -iname '*.ts' -not -iname '*.d.ts') '--out-extension:.js=.mjs' --sourcemap=inline --outdir=dist/esm --format=esm --target=es2022

# build cjs
echo 'Building cjs files...'
esbuild $(find ./src/js -iname '*.ts' -not -iname '*.d.ts') --sourcemap=inline --outdir=dist/cjs --format=cjs --target=es2022

# build dts
echo 'Building dts files...'
tsc --emitDeclarationOnly --outDir dist/dts

echo 'Mac script completed!'

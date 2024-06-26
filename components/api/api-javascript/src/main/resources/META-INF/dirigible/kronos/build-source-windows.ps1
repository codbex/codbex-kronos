# clean
rm dist -r -force

# install node modules
rm -rf ./node_modules
npm install

# Check whether the ABAP API can be transpiled correctly
npm run transpile

npm run lint

# Remove transpiled ABAP lib since it is not needed.
# It will be transpiled by the ABAP projects
rm -rf dist/abap

# build esm and cjs
Get-ChildItem -Recurse -Include *.ts -Exclude *.d.ts | ForEach-Object {
    $relativePath = $_.FullName.Substring((Get-Location).Path.Length + 1)
    $relativePath = $relativePath -replace '^src\\', ''
    $mjsOutputFile = ".\dist\esm\$relativePath" -replace '\.ts$', '.mjs'
    $cjsOutputFile = ".\dist\cjs\$relativePath" -replace '\.ts$', '.js'

    esbuild $_.FullName --outfile=$mjsOutputFile --sourcemap=inline --format=esm --target=es2022
    
    esbuild $_.FullName --outfile=$cjsOutputFile --sourcemap=inline --format=cjs --target=es2022
}

# build dts
tsc --emitDeclarationOnly --outDir dist\dts
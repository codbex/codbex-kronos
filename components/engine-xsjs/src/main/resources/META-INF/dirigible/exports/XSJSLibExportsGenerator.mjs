import { XSJSLibCompiler } from './XSJSLibCompiler.mjs'
import { digest } from '@dirigible/utils'
import { repository } from '@dirigible/platform'
import { bytes } from '@dirigible/io'

const ProblemsFacade = Java.type('org.eclipse.dirigible.components.api.platform.ProblemsFacade');

export class XSJSLibExportsGenerator {

  run(synchronizerTarget) {
    if(synchronizerTarget.isCollection()) {
      this._generateExportsRecursively(synchronizerTarget.getEntity(), false);
    }
    else {
      this._checkTableAndGenerateExportsIfNeeded(synchronizerTarget.getEntity(), false);
    }
  }

  _generateExportsRecursively(parentCollection, isCollectionCheckRequired) {
    if(isCollectionCheckRequired) {
      if(!this._validateCollection(parentCollection)) {
        return;
      }
    }
    
    const resourceNames = this._toJsArray(parentCollection.getResourcesNames());
    const collectionNames = this._toJsArray(parentCollection.getCollectionsNames());

    resourceNames
      .filter(resourceName => resourceName.endsWith(".xsjslib"))
      .map(resourceName => parentCollection.getResource(resourceName))
      .forEach(resource => {
        try {
          this._checkTableAndGenerateExportsIfNeeded(resource);
        } catch (e) {
          throw new Error("XSJSLibExportsGenerator: Cannot check if synchronisation is needed. Reason: ", e);
        }
      });

    collectionNames
      .map(collectionName => parentCollection.getCollection(collectionName))
      .forEach(collection => this._generateExportsRecursively(collection, true));
  }

  _logProblem(location, message) {
    ProblemsFacade.save(
      location,
      "SYNCHRONIZER",
      "",
      "",
      message,
      "",
      "XSJSLIB",
      "Engine-XSJS",
      "XSJSLibExportsGenerator.mjs",
      "Kronos"
    );
  }

  _toJsArray(array) {
    return [
      ...array
    ];
  }

  _checkTableAndGenerateExportsIfNeeded(resource, isResourceCheckRequired = true) {
    if(isResourceCheckRequired) {
      if(!this._validateResource(resource)) {
        return;
      }
    }
    
    console.log('Generating exports for: ' + resource.getPath());

    const content = bytes.byteArrayToText(resource.getContent());
    const location = resource.getPath();
    this._generateExportsFile(location, content);
  }

  _validateResource(resource) {
    if(!resource && !resource.exists()) {
      this._logProblem(
        resource.getPath(),
        "Requested .xsjslib synchronisation for resource '"
        + resource.getPath()
        + "' could not be completed. Resource does not exist in the registry.'"
      );
      return false;
    }

    if(!resource.getPath().endsWith(".xsjslib")) {
      return false;
    }

    return true;
  }

  _validateCollection(collection) {
    if (!collection.exists()) {
      this._logProblem(
        collection.getPath(),
        "Requested .xsjslib synchronisation for collection '"
        + collection.getPath()
        + "' could not be completed. Collection does not exist in the registry.'"
      );
      return false;
    }

    return true;
  }

  _generateExportsFile(location, content) {
    const compiler = new XSJSLibCompiler();
    const contentWithExports = compiler.appendExports(content);
    const generatedExportsFilePath = location + "_generated_exports.js";
    const exportsResource = repository.createResource(
      generatedExportsFilePath,
      contentWithExports,
      "application/javascript"
    );
  }

  _isContentChanged(foundEntry, content) {
      return foundEntry.HASH !== digest.md5Hex(content);
  }
}

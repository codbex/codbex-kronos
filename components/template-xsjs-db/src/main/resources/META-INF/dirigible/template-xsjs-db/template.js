/*
 * Generated by Eclipse Dirigible based on model and template.
 *
 * Do not modify the content as it may be re-generated again.
 */
exports.getTemplate = function() {
	return {
		"name": "XSJS DB API",
		"description": "XSJS DB API Template",
		"sources": [{
			"location": "/template-xsjs-db/service.xsjs.template", 
			"action": "generate",
			"rename": "{{fileName}}.xsjs"
		}],
		"parameters": [],
		"order": 20
	};
};

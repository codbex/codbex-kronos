/*
 * Generated by Eclipse Dirigible based on model and template.
 *
 * Do not modify the content as it may be re-generated again.
 */
exports.getTemplate = function() {
	return {
		"name": "HDB View",
		"description": "HDB View Template",
		"sources": [
		{
			"location": "/template-hdb-view/hdb.schema.template", 
			"action": "generate",
			"rename": "{{schemaName}}.hdbschema"
		},{
			"location": "/template-hdb-view/hdb.table.template", 
			"action": "generate",
			"rename": "{{tableName}}.hdbtable"
		},{
			"location": "/template-hdb-view/hdb.view.template", 
			"action": "generate",
			"rename": "{{viewName}}.hdbview"
		}],
		"parameters": [
		{
			"name": "schemaName",
			"label": "Schema Name"
		},{
			"name": "tableName",
			"label": "Table Name"
		},{
			"name": "viewName",
			"label": "View Name"
		}],
		"order": 10
	};
};

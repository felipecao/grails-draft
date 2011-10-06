package draft

import grails.converters.JSON

class DraftController {

    def messageSource
    static scaffold = Draft
    static defaultAction = "list"

    def editJSON = {
        def result
        def message = ""
        def state = "FAIL"
        def id

        // determine our action. Grid will pass a param called "oper"
        switch (params.oper) {
            // Delete Request
            case 'del':
                result = Draft.get(params.id)
                if (result) {
                    result.delete()
                    message = "Draft '${result.name} ${result.address}' Deleted"
                    state = "OK"
                }
                break;
            // Add Request
            case 'add':
                result = new Draft(params)
                break;
            // Edit Request
            case 'edit':
                // add or edit instruction sent
                result = Draft.get(params.id)
                result.properties = params
                break;
        }

        // If we aren't deleting the object then we need to validate and save.
        // Capture any validation messages to display on the client side
        if (result && params.oper != "del") {
            if (!result.hasErrors() && result.save(flush: true)) {
                message = "Draft  '${result.name} ${result.address}' " + (params.oper == 'add') ? "Added" : "Updated"
                id = result.id
                state = "OK"
            } else {
                message = "<ul>"
                result.errors.allErrors.each {
                    message += "<li>${messageSource.getMessage(it)}</li>"
                }
                message += "</ul>"
            }
        }

        //render [message:message, state:state, id:id] as JSON
        def jsonData = [messsage: message, state: state, id: id]
        render jsonData as JSON
    }

    def listJSON = {
        println "ENTER listJSON"

        def sortIndex = params.sidx ?: 'name'
        def sortOrder  = params.sord ?: 'asc'

        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1

        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        def drafts = Draft.createCriteria().list(max: maxRows, offset: rowOffset) {

            if (params.name)
                ilike('name', '%' + params.name + '%')

            if (params.address)
                ilike('address', '%' + params.address + '%')

            order(sortIndex, sortOrder).ignoreCase()
			//resultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
        }

        def totalRows = drafts.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def jsonCells = drafts?.collect {
            [
             	cell: [
                    it.name,
                    it.address
                ],
             	id: it.id
            ]
        }

        def jsonData= [rows: jsonCells,
                       page: currentPage,
                       records: totalRows,
                       total: numberOfPages]

        render jsonData as JSON
    }

}

package pages

import geb.Page

class ImdbSearchResultsPage extends Page {

    static at = { $(".findSearchTerm").text().contains("Matrix") }

}

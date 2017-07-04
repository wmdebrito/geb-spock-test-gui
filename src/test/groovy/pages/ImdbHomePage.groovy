package pages

import geb.Page
import modules.ImdbSearchModule

class ImdbHomePage extends Page {

    static url = "/"

    static at = { title.contains("IMDb - Movies, TV and Celebrities - IMDb") }

    static content = {
        searchHome { module ImdbSearchModule }
    }
}

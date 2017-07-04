package modules

import geb.Module

class ImdbSearchModule extends Module {
    static content = {
        searchBox { $("#navbar-query") }
        searchButton { $("#navbar-submit-button") }
    }


}

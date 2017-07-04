import geb.spock.GebReportingSpec
import pages.ImdbHomePage
import pages.ImdbSearchResultsPage

class HomePageSpec extends GebReportingSpec {

    def "Search Home Page Imdb"() {
        given:
        to ImdbHomePage
        when:
        searchHome.searchBox = "Matrix"
        searchHome.searchButton.click()

        then:
        at ImdbSearchResultsPage
    }
}
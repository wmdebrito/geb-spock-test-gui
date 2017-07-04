import org.apache.commons.lang3.SystemUtils
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.ie.InternetExplorerDriverService
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

cacheDriverPerThread = true
baseUrl = System.getenv("HOST") ?: "http://www.imdb.com/"
remoteServerUrl = System.getenv("REMOTE_SE") ?: "http://localhost:4444/wd/hub"
reportsDir = "target/geb-reports"
reportOnTestFailureOnly = true


File findDriverExecutable(env) {
    File file = new File("drivers").listFiles().findAll {
        !it.name.endsWith(".version")
    }.find {

        if (SystemUtils.IS_OS_LINUX) {
            it.name.contains("linux") && it.name.contains(env)
        } else if (SystemUtils.IS_OS_MAC) {
            it.name.contains("mac") && it.name.contains(env)
        } else if (SystemUtils.IS_OS_WINDOWS) {
            it.name.contains("windows") && it.name.contains(env)
        }
    }
    println file
    file
}

driver = {
    def service = new PhantomJSDriverService.Builder().usingPhantomJSExecutable(findDriverExecutable("phantomjs")).build()
    new PhantomJSDriver(service, new DesiredCapabilities())
//
//        def service = new InternetExplorerDriverService.Builder().usingDriverExecutable(findDriverExecutable("internetexplorer")).build()
//        new InternetExplorerDriver(service)
}




environments {
    // specify environment via -Dgeb.env=ie
    "ie" {
        def service = new InternetExplorerDriverService.Builder().usingDriverExecutable(findDriverExecutable("internetexplorer")).build()
        driver = { new InternetExplorerDriver(service) }
    }

    "chrome" {
        def service = new ChromeDriverService.Builder().usingDriverExecutable(findDriverExecutable("chrome")).build()
        driver = { new ChromeDriver(service) }
    }

    "ff" {
        System.properties.put("webdriver.gecko.driver", findDriverExecutable("gecko").absolutePath)
        driver = { new FirefoxDriver() }
    }

    "phantomjs" {
        def service = new PhantomJSDriverService.Builder().usingPhantomJSExecutable(findDriverExecutable("phantomjs")).build()
        driver = { new PhantomJSDriver(service, new DesiredCapabilities()) }
    }
    "remote.chrome" {
        driver = { new RemoteWebDriver(new URL(remoteServerUrl), DesiredCapabilities.chrome()) }
    }
    "remote.ff" {
        driver = { new RemoteWebDriver(new URL(remoteServerUrl), DesiredCapabilities.firefox()) }
    }
    "remote.ie" {
        driver = { new RemoteWebDriver(new URL(remoteServerUrl), DesiredCapabilities.internetExplorer()) }
    }
    'remote.phantomjs' {
        driver = { new RemoteWebDriver(new URL(remoteServerUrl), DesiredCapabilities.phantomjs()) }
    }

}

waiting {
    timeout = 10
    retryInterval = 0.1
    slow { timeout = 25 }
    reallySlow { timeout = 45 }
}



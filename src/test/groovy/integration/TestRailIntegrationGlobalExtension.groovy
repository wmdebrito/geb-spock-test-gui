package integration

import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.model.ErrorInfo
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo

class TestRailIntegrationGlobalExtension implements IGlobalExtension {

    void start() {}

    @Override
    void visitSpec(SpecInfo specInfo) {
        specInfo.addListener(new TestRailListener())
    }

    void stop() {}


    class TestRailListener extends AbstractRunListener {

        def features = new HashSet();
        def errors = new HashSet();

        @Override
        public void beforeFeature(FeatureInfo feature) {
            features.add(feature)
        }

        @Override
        void afterSpec(SpecInfo spec) {
            features.each {
                it ->
                    if (!errors.contains(it))
                        println ">>>>>> PASSED: " + it.name
            }
            errors.each {
                it -> println ">>>>>> FAILED: " + it.name
            }
            println ">>>>>> MOCK: send cases to test rail"
        }

        @Override
        void error(ErrorInfo error) {
            errors.add(error.method.feature)
        }
    }


}
package com.toomuchcoding.uptodate.finder

import com.toomuchcoding.uptodate.finder.jcenter.JCenterResponseProvider
import com.toomuchcoding.uptodate.finder.maven.MavenResponseProvider

import static com.toomuchcoding.uptodate.Jsons.JUNIT_RESPONSE
import static com.toomuchcoding.uptodate.Jsons.OLD_HIBERNATE_RESPONSE
import static com.toomuchcoding.uptodate.Xmls.HIBERNATE_CORE_META_DATA
import static com.toomuchcoding.uptodate.Xmls.OLD_JUNIT_META_DATA
import static com.toomuchcoding.uptodate.reporting.NewVersionProcessor.NEW_VERSIONS_MESSAGE_HEADER

@Mixin([MavenResponseProvider, JCenterResponseProvider])
class AllRepositoriesnewVersionFinderSpec extends NewFinderSpec {
   
    def setup() {
        project.extensions.uptodate.mavenRepo = "http://localhost:${MOCK_HTTP_SERVER_PORT}/maven/"
        project.extensions.uptodate.jCenterRepo = "http://localhost:${MOCK_HTTP_SERVER_PORT}/jcenter/" 
    }
    
    @Override
    protected Integer getHttpServerPort() {
        return MOCK_HTTP_SERVER_PORT
    }

    def "should list all dependencies, that have newer versions"() {
        given:
            stubInteractionForJcenter('org.hibernate', 'hibernate-core', HIBERNATE_CORE_META_DATA)
            stubInteractionForJcenter('junit', 'junit', OLD_JUNIT_META_DATA)
            stubInteractionForMavenCentral('org.hibernate' ,'hibernate-core', OLD_HIBERNATE_RESPONSE)
            stubInteractionForMavenCentral('junit' ,'junit', JUNIT_RESPONSE)
        and:
            project.dependencies.add(COMPILE_CONFIGURATION, 'org.hibernate:hibernate-core:4.2.9.Final')
            project.dependencies.add(TEST_COMPILE_CONFIGURATION, 'junit:junit:4.10')
        when:
            executeUptodateTask()
        then:
            1 * loggerProxy.lifecycle(_, NEW_VERSIONS_MESSAGE_HEADER +
                    "'junit:junit:4.11'\n" +
                    "'org.hibernate:hibernate-core:4.3.6.Final'")
    }    

}

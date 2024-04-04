currentBuild.displayName = "${BRANCH} ${TAGS}"
def nodeName = "${NODE}"
timeout(30) {
    node(nodeName) {
        safeCleanWs()
        try {
              stage("Tests") {
                parallel getTestStages()
              }
        } finally {
              stage("Allure") {
                generateReport()
              }
        }
    }
}

def getTestStages() {
    def arrayTags = "${TAGS}".split(",")
    def stages = [:]
    arrayTags.each { tag ->
        stages[tag] = {
                ws("${WORKSPACE}_" + tag) {
                safeCleanWs()
                test(tag)
                }
        }
    }
    return stages
}

def runFailedTests() {
    return ("${RUN_FAILED_TESTS}" == "true")
}

def test(tag) {
        stage("Download separate repository"){
           checkout scm: [
                        $class: 'GitSCM',
                        branches: [[name: '${BRANCH}']],
                        userRemoteConfigs: [[
                            credentialsId: 'f49ffee0-922e-4854-b886-82d9b8a2015a',
                            url: 'git@github.com:sergeykrylovich/Selenide_Rest-Assured_tests.git'
                        ]]
           ]
        }
    withEnv(["tag=${tag}"]) {
        if (runFailedTests()) {
            try {
                filter = "src/test/resources/${tag}_FailedTests.txt"
                getFailedTests(filter)
            } catch (err) {
                echo "Failed tests are not found"
                return
                exit 0
            }
        }


        try {
             runTests(tag)
        } catch (e) {
                labelledShell(label: 'Save failed tests', script: '''
                mv $WORKSPACE/src/test/resources/FailedTests.txt $WORKSPACE/src/test/resources/${tag}_FailedTests.txt
                failed_tests_file="$WORKSPACE/src/test/resources//${tag}_FailedTests.txt"
                if [ -s "$failed_tests_file" ]; then
                    echo "Failed tests are saved"
                else
                    echo "$failed_tests_file is empty"
                fi
                ''')
                archiveArtifacts(artifacts: "src/test/resources/${tag}_FailedTests.txt")
                throw("${e}")
            } finally {
                labelledShell(label: 'Zip test report', script: "tar -a -c -f report.zip build/allure-results")
                stash(name: "${tag}_report_stash", includes: "report.zip")
                echo("Stage was finished")
            }
        }
}

def safeCleanWs(){
            try {
                 cleanWs()
                } catch (err) {
                    echo "workspace already empty"
                }
}

def runTests(tag) {
    withEnv(["tag=${tag}"]) {
    if (runFailedTests()) {
        labelledShell(label: 'Run failed tests', script: '''
        tests_file="$WORKSPACE/src/test/resources/${tag}_FailedTests.txt"
        if [ -f "$tests_file" ]; then
            if [ -s "$tests_file" ]; then
                chmod +x gradlew
                ./gradlew test $(cat $tests_file) -Dhost=${SELENOID_HOST}
            else
                echo "$tests_file is empty"
            fi
        fi
        ''')
    } else {
            labelledShell(label: 'Run tests', script: '''
            chmod +x gradlew
            ./gradlew clean testsWithTags -x test -DcustomTags=$tag -Dhost=${SELENOID_HOST}
            ''')
        }
    }
}


def getFailedTests(filter) {
    copyArtifacts(
        projectName: "${JOB_NAME}",
        filter: "$filter",
        flatten: false,
        parameters: "BRANCH=$BRANCH",
        selector: lastCompleted()
    )
}

def generateReport() {
            def arrayTags = "${TAGS}".split(",")
            sh("mkdir -p allure-results")
            arrayTags.each { tag ->
                try {
                    unstash(name: "${tag}_report_stash")
                    labelledShell(label: 'Unzip test report', script: '''
                    mkdir -p temp_report
                    tar xvf report.zip -C temp_report
                    mv temp_report/build/allure-results/* allure-results
                    rm -r temp_report
                    rm -r report.zip
                    ''')
                } catch (err) {
                    echo "Test report for ${tag} doesn't exist. \nError message: \n${err}"
                }
            }

        allure([
            includeProperties: true,
            jdk              : '',
            properties       : [],
            reportBuildPolicy: 'ALWAYS',
            results          : [[path: 'allure-results']]
        ])
}

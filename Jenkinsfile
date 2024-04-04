timeout(30) {
    node("windows-agent") {
       echo "Download project"
       checkout scmGit: [
                branches: [[name: ${BRANCH}]],
                userRemoteConfigs: [[
                         credentialsId: 'f49ffee0-922e-4854-b886-82d9b8a2015a',
                         url: 'git@github.com:sergeykrylovich/Selenide_Rest-Assured_tests.git'
                         ]]
                ]
       try {
       labelledShell(label: 'Save failed tests', script: '''
       chmod +x gradlew
       ./gradlew clean testsWithTags -x test -DcustomTags=UI
       ''')
       } finally {
       archiveArtifacts(artifacts: "src/test/resources/FailedTests.txt")
       allure([
                   includeProperties: true,
                   jdk              : '',
                   properties       : [],
                   reportBuildPolicy: 'ALWAYS',
                   results          : [[path: 'build/allure-results']]
               ])
      }
    }

}
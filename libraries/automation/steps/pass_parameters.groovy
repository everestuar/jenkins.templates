void call(Map params){
    def version = params.version
    def environment = params.environment ?: 'sandbox'
    def namespace = params.namespace ?: 'develop'
    def run_environment = params.run_environment ?: 'test'
    def payload = params.payload ?: ''

    def repo = ""

    withFolderProperties{
        repo = env.REPO
    }

    def repo_name = repo.split('/').last()
    def named_profile = "to_be_deleted_jenkins"

    node("built-in") {
        try {
            stage('Print parameters') {
                sh "echo \"Repo: ${repo}\""
                sh "echo ${repo_name}"
                sh "echo \"Namespace: ${namespace}\""
                sh "echo \"Run Environment: ${run_environment}\""
                sh "echo \"Payload: ${payload}\""

                def jsonObj = readJSON text: "${payload}"
                echo "$jsonObj"

                // dir("LocalRepository") {
                //     sh "ls"
                //     sh "git remote set-url --push origin codecommit::us-west-2://${repo_name}"
                //     sh "git fetch -p origin"
                //     sh "git push --mirror --follow-tags"
                // }

                // sh "rm -rf LocalRepository"
            }

        } catch (err) {
            echo "${err}"
        }
    }

}

void call(Map params){
    def version = params.version
    def environment = params.environment ?: 'sandbox'
    def namespace = params.namespace ?: 'develop'

    def repo = ""

    withFolderProperties{
        repo = env.REPO
    }

    def repo_name = repo.split('/').last()
    def named_profile = "to_be_deleted_jenkins"

    node("built-in") {
        stage('Replicate to second region') {
            sh "echo ${repo}"
            sh "echo ${repo_name}"
            sh "pwd"
            sh "rm -rf LocalRepository"
            sh "git clone --mirror codecommit::us-east-2://${repo_name} LocalRepository"
            sh "ls -ltr"

            dir("LocalRepository") {
                sh "ls"
                sh "git remote set-url --push origin codecommit::us-west-2://${repo_name}"
                sh "git fetch -p origin"
                sh "git push --mirror --follow-tags"
            }

            sh "rm -rf LocalRepository"
        }

        stage("Replicate to second account") {
            sh "echo ${repo}"
            sh "echo ${repo_name}"
            sh "pwd"
            sh "rm -rf BackupRepository"
            sh "git clone --mirror codecommit::us-east-2://${repo_name} BackupRepository"
            sh "ls -ltr"

            dir("BackupRepository") {
                sh "ls"
                sh "git remote set-url --push origin codecommit::us-east-2://${named_profile}@${repo_name}-bkp"
                sh "git fetch -p origin"
                sh "git push --mirror --follow-tags"
            }

            sh "rm -rf BackupRepository"
        }
    }

}
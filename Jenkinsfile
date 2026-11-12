pipeline {
    agent { dockerfile true }

    parameters {
        gitParameter branchFilter: 'origin/(.*)', defaultValue: 'wrynose', selectedValue: 'DEFAULT', name: 'BRANCH', type: 'PT_BRANCH', description: 'branch to build'
        choice choices: ['qemux86-64', 'raspberrypi5', 'raspberrypi4-64'], description: 'select machine', name: 'MACHINE'
        choice choices: ['tor-demo-image'], description: 'select image', name: 'IMAGE'
        choice choices: ['no', 'yes'], description: 'clean workspace', name: 'CLEAN'
        choice choices: ['no', 'yes'], description: 'build sdk', name: 'SDK'
    }

    stages {
 
        stage('Clean') {
            when {
                expression { params.CLEAN == 'yes' }
            }
            steps {
               sh "git clean -fdx"
            }
        }

        stage('Build-Image') {
            steps {
                sh "KAS_MACHINE=${params.MACHINE} KAS_TARGET=${params.IMAGE} kas build --force-checkout --update kas-tor.yml"
                archiveArtifacts artifacts: "build/tmp/deploy/images/${params.MACHINE}/${params.IMAGE}-${params.MACHINE}.rootfs-*" ,
                                             followSymlinks: true,
                                             fingerprint: true,
                                             onlyIfSuccessful: true
            }
        }

    }
}

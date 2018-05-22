package sx

class ImportadorDeValesJob {

def importadorDeVales

    static triggers = {
         cron name:   'impVales',   startDelay: 10000, cronExpression: '0 0/3 * * * ?'
    }

    def execute() {

        println "************************************************************"
        println "*                                                          *"
        println "*                    Importando Vales     ${new Date()}  "
        println "*                                                          *"
        println "************************************************************"


        try {
            importadorDeVales.importar()
        }catch (Exception e){
            e.printStackTrace()
        }

    }
}

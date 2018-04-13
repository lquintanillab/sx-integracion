package sx

class ImportadorDepositosJob {

def importadorDeDepositos

    static triggers = {
       cron name:   'impDepositos',   startDelay: 10000, cronExpression: '0 0/3 8-19 ? * MON-SAT'
    }

    def execute() {

      println "************************************************************"
       println "*                                                          *"
       println "*                    Importando Depositos                  *"
       println "*           ${new Date()}                   *"
       println "*                                                          *"
       println "************************************************************"

       try {
          importadorDeDepositos.importar()
       }catch (Exception e){
           e.printStackTrace()
       }
      
    }
}

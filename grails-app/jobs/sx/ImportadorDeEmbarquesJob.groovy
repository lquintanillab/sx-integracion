package sx

class ImportadorDeEmbarquesJob {

    def importadorDeEmbarque

    static triggers = {
      cron name:   'impEmb',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*            Importando Embarques     ${new Date()}  "
      println "*                                                          *"
      println "************************************************************"

      try{
          importadorDeEmbarque.importar()
      }catch(Exception e){
        e.printStackTrace()
      }
    }
}

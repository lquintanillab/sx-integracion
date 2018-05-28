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
          immportadorDeEmbarque.importar()
      }catch(Exception e){
        e.printStackTrace()
      }
    }
}

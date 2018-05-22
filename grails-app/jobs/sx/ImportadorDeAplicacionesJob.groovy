package sx

class ImportadorDeAplicacionesJob {

    def importadorDeAplicacionesCOD

    static triggers = {
        cron name:   'impApl',   startDelay: 20000, cronExpression: '0 0/15 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
    println "*            Importando aplicaciones COD    ${new Date()}  "
      println "*                                                          *"
      println "************************************************************"

      try{
        importadorDeAplicacionesCOD.importar()
      }catch(Exception e){
        e.printStackTrace()
      }

    }
}

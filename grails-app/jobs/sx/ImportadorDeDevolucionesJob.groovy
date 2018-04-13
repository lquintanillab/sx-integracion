package sx

class ImportadorDeDevolucionesJob {

def importadorDeDevoluciones

    static triggers = {
       cron name:   'impDevoluciones',   startDelay: 10000, cronExpression: '0 30 20 * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*                    Importando Devoluciones               *"
      println "*                                                          *"
      println "************************************************************"

      try{
          importadorDeDevoluciones.importar()
      }catch(Exception e){
        e.printStackTrace()
      }

    }
}

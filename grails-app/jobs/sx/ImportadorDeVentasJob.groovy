package sx

class ImportadorDeVentasJob {
  def importadorDeVentas

    static triggers = {
      cron name:   'impVtas',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*            Importando Ventas         ${new Date()}  "
      println "*                                                          *"
      println "************************************************************"

      try{
        importadorDeVentas.importar()
      }catch(Exception e){
        e.printStackTrace()
      }

    }
}

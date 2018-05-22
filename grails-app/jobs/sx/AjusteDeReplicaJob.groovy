package sx

class AjusteDeReplicaJob {

def sincronizacionService

    static triggers = {
      cron name:   'ajusteReplica',   startDelay: 10000, cronExpression: '0 0/1 * * * ?'
    }

  def execute() {
      println "************************************************************"
      println "*                                                          *"
      println "*                    Ajustando Replica ${new Date()}  "
      println "*                                                          *"
      println "************************************************************"


      try {
          sincronizacionService.replicaClientesCredito()
      }catch (Exception e){
          e.printStackTrace()
      }

      try {

         sincronizacionService.depuraReplicaOficinas()
      }catch (Exception e){
          e.printStackTrace()
      }


    }
}

package sx.exportacion

class ExportadorDeCalle4Job {

  def exportadorDeClientes
  def exportadorDeClientesCredito
  def exportadorDeProductos
  def exportadorDeExistencia
  def exportadorDeVales
  def exportadorDeTraslados

    static triggers = {
    cron name:   'expCalle4',   startDelay: 20000, cronExpression: '0 0/7 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Calle 4                    *"
      println "*                                                          *"
      println "************************************************************"
      def sucursal = 'CALLE 4'

      try{
         exportadorDeVales.exportarSucursal(sucursal)
      }catch (Exception e){
             e.printStackTrace()
     }
     try{
        exportadorDeTraslados.exportarSucursal(sucursal)
     }catch (Exception e){
            e.printStackTrace()
    }

    }
}

package sx.exportacion

class ExportadorDeBolivarJob {

  def exportadorDeClientes
  def exportadorDeClientesCredito
  def exportadorDeProductos
  def exportadorDeExistencia
  def exportadorDeVales
  def exportadorDeTraslados

    static triggers = {
      cron name:   'expBolivar',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {
/*
      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Bolivar                    *"
      println "*                                                          *"
      println "************************************************************"
      def sucursal = 'BOLIVAR'

          try{
             exportadorDeClientesCredito.exportar(sucursal)
          }catch (Exception e){
                 e.printStackTrace()
         }
         try{
            exportadorDeClientes.exportar(sucursal)
         }catch (Exception e){
                e.printStackTrace()
          }
          try{
             exportadorDeProductos.exportar(sucursal)
          }catch (Exception e){
                 e.printStackTrace()
         }
         try{
            exportadorDeExistencia.exportar(sucursal)
         }catch (Exception e){
                e.printStackTrace()
        }
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
*/
    }

}

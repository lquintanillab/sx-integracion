package sx.exportacion

class ExportadorDeVertizJob {

  def exportadorDeClientes
  def exportadorDeClientesCredito
  def exportadorDeProductos
  def exportadorDeExistencia
  def exportadorDeVales
  def exportadorDeTraslados

    static triggers = {
      cron name:   'expVertiz',   startDelay: 20000, cronExpression: '0 0/7 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*                   Ex portando Vertiz                     *"
      println "*                                                          *"
      println "************************************************************"

      def sucursal = 'VERTIZ 176'

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
/*
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

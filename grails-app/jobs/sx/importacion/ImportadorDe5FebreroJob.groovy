package sx.importacion

class ImportadorDe5FebreroJob {

  def importadorDeExistencias
  def importadorDeClientes
  def importadorDeVales
  def importadorDeTraslados


    static triggers = {
    cron name:   'imp5Febrero',   startDelay: 20000, cronExpression: '0 0/4 * * * ?'
    }

    def execute() {

     println "************************************************************"
     println "*                                                          *"
     println "*                    Importando 5 Febrero                  *"
     println "*                                                          *"
     println "************************************************************"

     def sucursal = 'CF5FEBRERO'

     try{
        println "importando Vales: "+sucursal
        importadorDeVales.importarSucursal(sucursal)
     }catch (Exception e){
            e.printStackTrace()
    }
    try{
       println "importando Traslados: "+sucursal
       importadorDeTraslados.importarSucursal(sucursal)
    }catch (Exception e){
           e.printStackTrace()
   }
/*
     try{
       println "importando Existencias: "+sucursal
        importadorDeExistencias.importar(sucursal)
     }catch (Exception e){
            e.printStackTrace()
    }

    try{
       println "importando Clientes: "+sucursal
       importadorDeClientes.importar(sucursal)
    }catch (Exception e){
           e.printStackTrace()
   }
   try{
      println "importando ComunicacionEmpresa: "+sucursal
      importadorDeClientes.importarComunicacionEmpresa(sucursal)
   }catch (Exception e){
          e.printStackTrace()
  }

  try{
     println "importando Vales: "+sucursal
     importadorDeVales.importarSucursal(sucursal)
  }catch (Exception e){
         e.printStackTrace()
 }
 try{
    println "importando Traslados: "+sucursal
    importadorDeTraslados.importarSucursal(sucursal)
 }catch (Exception e){
        e.printStackTrace()
}
*/
    }
}

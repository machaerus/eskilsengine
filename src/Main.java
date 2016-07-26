import scene_logic.SceneLogic;
import scene_structure.Scene;
import scene_structure.SceneLoader;


/*///////////////////////////////////////////////////////////////////////////////////////////



 		                                                                                  
	8 8888888888     d888888o.   8 8888     ,88'  8 8888 8 8888   d88     d888888o.   
	8 8888         .`8888:' `88. 8 8888    ,88'   8 8888 8 8888   `Y8   .`8888:' `88. 
	8 8888         8.`8888.   Y8 8 8888   ,88'    8 8888 8 8888     `   8.`8888.   Y8 
	8 8888         `8.`8888.     8 8888  ,88'     8 8888 8 8888         `8.`8888.     
	8 888888888888  `8.`8888.    8 8888 ,88'      8 8888 8 8888          `8.`8888.    
	8 8888           `8.`8888.   8 8888 88'       8 8888 8 8888           `8.`8888.   
	8 8888            `8.`8888.  8 888888<        8 8888 8 8888            `8.`8888.  
	8 8888        8b   `8.`8888. 8 8888 `Y8.      8 8888 8 8888        8b   `8.`8888. 
	8 8888        `8b.  ;8.`8888 8 8888   `Y8.    8 8888 8 8888        `8b.  ;8.`8888 
	8 888888888888 `Y8888P ,88P' 8 8888     `Y8.  8 8888 8 888888888888 `Y8888P ,88P' 
	
							~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
						~~~~~~								~~~~~~
		                                                                            
		8 8888888888       ,o888888o.     8 8888         8 8888  `8.`8888.      ,8' 
		8 8888          . 8888     `88.   8 8888         8 8888   `8.`8888.    ,8'  
		8 8888         ,8 8888       `8b  8 8888         8 8888    `8.`8888.  ,8'   
		8 8888         88 8888        `8b 8 8888         8 8888     `8.`8888.,8'    
		8 888888888888 88 8888         88 8 8888         8 8888      `8.`88888'     
		8 8888         88 8888         88 8 8888         8 8888       `8. 8888      
		8 8888         88 8888        ,8P 8 8888         8 8888        `8 8888      
		8 8888         `8 8888       ,8P  8 8888         8 8888         8 8888      
		8 8888          ` 8888     ,88'   8 8888         8 8888         8 8888      
		8 8888             `8888888P'     8 888888888888 8 888888888888 8 8888      
		
		
								
								
		Dom torfowy 4 life xD
		
		-------------------------------
	
		Autor silnika: Jan Szwagierczak
		
		-------------------------------
											
 
 
///////////////////////////////////////////////////////////////////////////////////////////////
 
 
 		TODO:	
 			
 		* 	dodaj tag description wszędzie, gdzie może się przydać
 			wyświetlania alternatywnego opisu dla elementu gry, 			
 			kiedy gramy bez GUI (w szczególności: Event) 
 		*	usuń ze wszystkich miejsc, gdzie nie będzie wyświetlany,
 		 	jako opis wystarczy opis XML
 		*	klasy do wyświetlania obiektów i zdarzeń
 		*	zmień składnię warunków w plikach scen: 
 			- znaczniki <condition> niech zawierają instrukcje typu:
 				if ( var1=1 & var2>0 ) | var1!=1
 			- instrukcje zagnieżdżone w tekście:
 				<?var zmienna ?>
 				<?c var1>1 itd... ?> ... <?cend?>
 
 
 
/////////////////////////////////////////////////////////////////////////////////////////////*/


public class Main {

	public static void main(String[] args) {
		
		Boolean isGraphicsOn = false;
		SceneLoader LOADER = SceneLoader.getInstance();
		Scene testScene = LOADER.Build("test",1);

		SceneLogic sceneLogic = new SceneLogic(testScene, isGraphicsOn);
		sceneLogic.playScene();
		
		System.out.println("stop");
		
	}

}

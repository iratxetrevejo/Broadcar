package broad.car.broadcar.speech;

import java.util.ArrayList;

public class CommandMatcher {

	ArrayList<String> matches=null;
	CommandDictionary dictionary;
	boolean detectado;
	
	boolean KEY_BOOL_ACTIVATE=false;
	boolean KEY_BOOL_DESACTIVATE=false;
	boolean KEY_BOOL_ALERT=false;
	boolean KEY_BOOL_CRASHES=false;
	boolean KEY_BOOL_HEAVY_TRAFFIC=false;
	boolean KEY_BOOL_LOW_VISIBILITY=false;
	boolean KEY_BOOL_ROAD_STATE=false;
	boolean KEY_BOOL_VEHICLE_NO_VISIBLE=false;
	boolean KEY_BOOL_WORKS=false;
	
	public CommandMatcher(){
		setMatchesList(matches);
		dictionary=new CommandDictionary();
	}
	
    public ArrayList<String> getMatchesList(){
    	return this.matches;
    }
    public void  setMatchesList(ArrayList<String> matches){
    	this.matches=matches;
    }
    
    public void searchForSpeechCommand(){
    	if(matches!=null){
    		detectado=true;
    		//KEY_BOOL_ACTIVATE
    		for(int i=0;i<getMatchesList().size();i++){
    			String[] splitted = getMatchesList().get(i).split(" ");
    			for(int j=0;j<splitted.length;j++){
    				for(int k=0;k<dictionary.KEY_STRING_ACTIVATE.size();k++){		
        				if(dictionary.KEY_STRING_ACTIVATE.get(k).equals(splitted[j])){
                			KEY_BOOL_ACTIVATE=true;	
                			break;
        				}	
        			}
        		}break;
        	}
    		//KEY_BOOL_DESACTIVATE
    		for(int i=0;i<getMatchesList().size();i++){
    			String[] splitted = getMatchesList().get(i).split(" ");
    			for(int j=0;j<splitted.length;j++){
    				for(int k=0;k<dictionary.KEY_STRING_DESACTIVATE.size();k++){		
        				if(dictionary.KEY_STRING_DESACTIVATE.get(k).equals(splitted[j])){
                			KEY_BOOL_DESACTIVATE=true;	
                			break;
        				}	
        			}
        		}break;
        	}
    		//KEY_BOOL_ALERT
    		for(int i=0;i<getMatchesList().size();i++){
    			String[] splitted = getMatchesList().get(i).split(" ");
    			for(int j=0;j<splitted.length;j++){
    				for(int k=0;k<dictionary.KEY_STRING_ALERT.size();k++){		
        				if(dictionary.KEY_STRING_ALERT.get(k).equals(splitted[j])){
                			KEY_BOOL_ALERT=true;	
                			break;
        				}	
        			}
        		}break;
        	}
    		//KEY_BOOL_CRASHES
    		for(int i=0;i<getMatchesList().size();i++){
    			String[] splitted = getMatchesList().get(i).split(" ");
    			for(int j=0;j<splitted.length;j++){
    				for(int k=0;k<dictionary.KEY_STRING_CRASHES.size();k++){		
        				if(dictionary.KEY_STRING_CRASHES.get(k).equals(splitted[j])){
                			KEY_BOOL_CRASHES=true;	
                			break;
        				}	
        			}
        		}break;
        	}
    		//KEY_BOOL_HEAVY_TRAFFIC
    		for(int i=0;i<getMatchesList().size();i++){
    			String[] splitted = getMatchesList().get(i).split(" ");
    			for(int j=0;j<splitted.length;j++){
    				for(int k=0;k<dictionary.KEY_STRING_HEAVY_TRAFFIC.size();k++){		
        				if(dictionary.KEY_STRING_HEAVY_TRAFFIC.get(k).equals(splitted[j])){
                			KEY_BOOL_HEAVY_TRAFFIC=true;	
                			break;
        				}	
        			}
        		}break;
        	}
    		//KEY_BOOL_LOW_VISIBILITY
    		for(int i=0;i<getMatchesList().size();i++){
    			String[] splitted = getMatchesList().get(i).split(" ");
    			for(int j=0;j<splitted.length;j++){
    				for(int k=0;k<dictionary.KEY_STRING_LOW_VISIBILITY.size();k++){		
        				if(dictionary.KEY_STRING_LOW_VISIBILITY.get(k).equals(splitted[j])){
                			KEY_BOOL_LOW_VISIBILITY=true;	
                			break;
        				}	
        			}
        		}break;
        	}
    		//KEY_BOOL_ROAD_STATE
    		for(int i=0;i<getMatchesList().size();i++){
    			String[] splitted = getMatchesList().get(i).split(" ");
    			for(int j=0;j<splitted.length;j++){
    				for(int k=0;k<dictionary.KEY_STRING_ROAD_STATE.size();k++){		
        				if(dictionary.KEY_STRING_ROAD_STATE.get(k).equals(splitted[j])){
                			KEY_BOOL_ROAD_STATE=true;	
                			break;
        				}	
        			}
        		}break;
        	}
    		//KEY_BOOL_VEHICLE_NO_VISIBLE
    		for(int i=0;i<getMatchesList().size();i++){
    			String[] splitted = getMatchesList().get(i).split(" ");
    			for(int j=0;j<splitted.length;j++){
    				for(int k=0;k<dictionary.KEY_STRING_VEHICLE_NO_VISIBLE.size();k++){		
        				if(dictionary.KEY_STRING_VEHICLE_NO_VISIBLE.get(k).equals(splitted[j])){
        					KEY_BOOL_VEHICLE_NO_VISIBLE=true;	
                			break;
        				}	
        			}
        		}break;
        	}
    		//KEY_BOOL_WORKS
    		for(int i=0;i<getMatchesList().size();i++){
    			String[] splitted = getMatchesList().get(i).split(" ");
    			for(int j=0;j<splitted.length;j++){
    				for(int k=0;k<dictionary.KEY_STRING_WORKS.size();k++){		
        				if(dictionary.KEY_STRING_WORKS.get(k).equals(splitted[j])){
                			KEY_BOOL_WORKS=true;	
                			break;
        				}	
        			}
        		}break;
        	}
    	}	
    	commandExecutor();
    }

    private void commandExecutor(){
    	if(KEY_BOOL_ACTIVATE){
    		if(KEY_BOOL_ALERT){
    			if(!KEY_BOOL_CRASHES && !KEY_BOOL_HEAVY_TRAFFIC && !KEY_BOOL_LOW_VISIBILITY && 
    					!KEY_BOOL_ROAD_STATE && !KEY_BOOL_VEHICLE_NO_VISIBLE && !KEY_BOOL_WORKS){
    				//ACTIVAR TODAS LAS ALERTAS
    			}
    		}else{
				if(KEY_BOOL_CRASHES){/*Activar Alertas de accidentes*/}
				if(KEY_BOOL_HEAVY_TRAFFIC){/*Activar Alertas de trafico*/}
				if(KEY_BOOL_LOW_VISIBILITY){/*Activar Alertas de poca visibilidad*/}
				if(KEY_BOOL_ROAD_STATE){/*Activar Alertas de estado carretera*/}
				if(KEY_BOOL_VEHICLE_NO_VISIBLE){/*Activar Alertas de vehiculo no visible*/}
				if(KEY_BOOL_WORKS){/*Activar Alertas de obras*/}
				
			}		
    	}else if(KEY_BOOL_DESACTIVATE){
    		if(KEY_BOOL_ALERT){
    			if(!KEY_BOOL_CRASHES && !KEY_BOOL_HEAVY_TRAFFIC && !KEY_BOOL_LOW_VISIBILITY && 
    					!KEY_BOOL_ROAD_STATE && !KEY_BOOL_VEHICLE_NO_VISIBLE && !KEY_BOOL_WORKS){
    				//DESACTIVAR TODAS LAS ALERTAS
    			}
    		}else{
				if(KEY_BOOL_CRASHES){/*Desactivar Alertas de accidentes*/}
				if(KEY_BOOL_HEAVY_TRAFFIC){/*Desactivar Alertas de trafico*/}
				if(KEY_BOOL_LOW_VISIBILITY){/*Desactivar Alertas de poca visibilidad*/}
				if(KEY_BOOL_ROAD_STATE){/*Desactivar Alertas de estado carretera*/}
				if(KEY_BOOL_VEHICLE_NO_VISIBLE){/*Desactivar Alertas de vehiculo no visible*/}
				if(KEY_BOOL_WORKS){/*Desactivar Alertas de obras*/}
				
			}	
    	}
    }    
}

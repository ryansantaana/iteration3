public class ClientRequest {
    private String command;
    private String data;
    private String additionalData;

    public ClientRequest(String type, String data) {
        this.command = type;
        this.data = data;
    }

    public String getCommand() {
        return command;
    }

    public String getData() {
        return data;
    }
    public String getAdditionalData(){
    	return this.additionalData;
    }
    public void setAdditionalData(String dataIn){
    	additionalData = dataIn;
    }
}
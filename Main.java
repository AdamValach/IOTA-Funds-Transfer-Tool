import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.iota.jota.IotaAPI;
import org.iota.jota.builder.AddressRequest;
import org.iota.jota.dto.response.GetBalancesAndFormatResponse;
import org.iota.jota.dto.response.GetNodeInfoResponse;
import org.iota.jota.model.Input;
import org.iota.jota.model.Transfer;
import org.iota.jota.pow.pearldiver.PearlDiverLocalPoW;
import org.iota.jota.utils.TrytesConverter;

import java.util.*;

public class Main extends Application {
    private IotaAPI iotaAPI;
    private MainView theView;

    private static final String protocol = "https"; //YOUR NODE INFO GOES HERE
    private static final String host = "nodees.thetangle.org"; //YOUR NODE INFO GOES HERE
    private static final int port = 443; //YOUR NODE INFO GOES HERE

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        theView = new MainView(primaryStage);
        theView.setup();
        theView.button0.setOnAction(e -> {
            try {
                apiSetup();
            } catch (Exception ex) {
                theView.errorWindow("ERROR CONNECTING TO NODE, PLEASE CHECK THE NODE ADDRESS AND RESTART THE APP", "ERROR");
                System.out.println("ERROR CONNECTING TO NODE, PLEASE CHECK THE NODE ADDRESS AND RESTART THE APP");
            }
        });
        theView.button1.setOnAction(e -> {
            theView.resetFields(125);
            if(checkSeed(theView.field1.getText()) & checkAddress(theView.field2.getText()) & checkAmount(theView.field5.getText())) sendTransaction();
        });
        theView.button2.setOnAction(e -> {
            theView.resetFields(125);
            if(checkSeed(theView.field1.getText())) getAddress();
        });
        theView.button3.setOnAction(e -> {
            theView.resetFields(125);
            if(checkSeed(theView.field1.getText())) showBalance();
        });
    }

    public void apiSetup() {
        iotaAPI = new IotaAPI.Builder()
                .protocol(protocol)
                .localPoW(new PearlDiverLocalPoW())
                .host(host)
                .port(port)
                .build();
        GetNodeInfoResponse response = iotaAPI.getNodeInfo();

        if(iotaAPI != null) {
            theView.connectionUpdate(protocol + "://" + host + ":" + port);
        }
        System.out.println(response);
    }

    public void sendTransaction() {
        List<Transfer> transfers = new ArrayList<>();
        List<Input> inputlist = new ArrayList<>();
        transfers.add(new Transfer(theView.field2.getText(), Integer.parseInt(theView.field5.getText()), TrytesConverter.asciiToTrytes("Sent with IOTA Funds Transfer Tool"),"TX"));
        if(theView.field5.getText().equals("0")) iotaAPI.sendTransfer(theView.field1.getText(), 2, 2, 14, transfers, null, null, false, true, null);
        else {
            inputlist.addAll(iotaAPI.getInputs(theView.field1.getText(), 2, 0, 30, 0).getInputs());
            iotaAPI.sendTransfer(theView.field1.getText(), 2, 2, 14, transfers, inputlist, null, false, true, null);
        }
        System.out.println("Transaction sent.");
    }

    public void getAddress() {
        AddressRequest addressRequest = new AddressRequest.Builder(theView.field1.getText(), 2).checksum(true).addSpendAddresses(false).build();
        System.out.println(iotaAPI.generateNewAddresses(addressRequest).getAddresses().get(0));
    }

    public void showBalance() {
        GetBalancesAndFormatResponse res = iotaAPI.getInputs(theView.field1.getText(), 2, 0, 30, 0);
        System.out.println(res);
    }

    public boolean checkSeed(String seed) {
        if(seed.length() != 81) {
            theView.incorrectField(1);
            return false;
        }
        else for(int i = 0; i < 81; i++) if(!((seed.charAt(i) > 64 && seed.charAt(i) < 91) || seed.charAt(i) == 57)) {
            theView.incorrectField(1);
            return false;
        }
        return true;
    }

    public boolean checkAmount(String amount) {
        if(StringUtils.isNumeric(amount) && !amount.isEmpty()) return true;
        else {
            theView.incorrectField(5);
            return false;
        }
    }

    public boolean checkAddress(String address) {
        if(address.length() != 90) {
            theView.incorrectField(2);
            return false;
        }
        else for(int i = 0; i < 90; i++) if(!((address.charAt(i) > 64 && address.charAt(i) < 91) || address.charAt(i) == 57)) {
            theView.incorrectField(2);
            return false;
        }
        return true;
    }
}
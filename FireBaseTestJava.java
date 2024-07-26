package mixpaneltest;

import base.BaseTestClass;
import com.api.APIUtilities;
import com.api.ApiResponse;

import com.global.TestGroup;
//import com.google.auth.Credentials;
//import com.google.auth.oauth2.GoogleCredentials;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FirebaseTest {

    ApiResponse apiResponse= ApiResponse.getInstance();
    APIUtilities apiUtilities= APIUtilities.getInstance();

    public HashMap<String, String> getHeaderMap() {
        HashMap<String, String> hashmap= new HashMap<>();
        hashmap.put("authorization", "SAPISIDHASH 1683028580_5b5a89a722ea6e5b710000cbdaac22fb5be5c91a");
        hashmap.put("cookie", "HSID=Ar9wcG9DlvfiltIXC; SSID=AjDLTAMGCW-hFas7z; APISID=ymgCEozvQkPBJfTz/AM-KFb-rzagDRnkJt; SAPISID=_dIiA8IV_XHeCYtD/ABjhNWzeAzPpsqIZT; __Secure-1PAPISID=_dIiA8IV_XHeCYtD/ABjhNWzeAzPpsqIZT; __Secure-3PAPISID=_dIiA8IV_XHeCYtD/ABjhNWzeAzPpsqIZT; SEARCH_SAMESITE=CgQItZcB; S=billing-ui-v3=EParzshHCNleWDGIUo_Mq7Z7C8iVT9CW:billing-ui-v3-efe=EParzshHCNleWDGIUo_Mq7Z7C8iVT9CW; SID=WAiImS2X33P0FUgHu2kZ0dp6tsk78Ukv8ifZJG318ScNyt2mruPwJUHqY0ejk1Wc_fX98Q.; __Secure-1PSID=WAiImS2X33P0FUgHu2kZ0dp6tsk78Ukv8ifZJG318ScNyt2mpmx8KK6Gsu5Oc6drAI5QCA.; __Secure-3PSID=WAiImS2X33P0FUgHu2kZ0dp6tsk78Ukv8ifZJG318ScNyt2mC2bjk-o6SBRVzDntazv9bQ.; 1P_JAR=2023-05-02-11; AEC=AUEFqZcud6CM_IGIxNmNyDUWbDyMTtjpHfV8N6ObVI7mPKBj-GpmZHu4Fb8; NID=511=hWC4WugoW0HmL6HqG1nXnY4ZfwP0sHLkVTmng0gMvdwQ8o3hx56WwYg-U7uPspQIFDiEYpMb5jcSWMBo-1WWKbDCqVMUPto2ae62NYz6lx55zso1TICYUT23lZ1kfQhy94GNqlFkIdmnJnFppnZEK7sD9wHjoE--oLoHkBUnrVMTnNu_z4aPEsSRgoGnsk4qUq7WN-_YnuL6TnQuOkFzVttM-31SDeFG_bdsASBELUew1JZDxHRpMy-8zXF7t7MoCxxIabmDD2ULNIIjyI5FerbquoF-2PWvNpNGyL1C-RA9kYWLVoya9rzfHpA8YNOhKhEo7jtou8sH9P35RJCGLewLZGxQD-8cLN-mV3Ww5d8daoOVmhTDXNdI2J1JisCLS7MMy7gesPVdW0fp; SIDCC=AP8dLtyXPI3ylws6NNtk4fgnO5R2yCHPtfJKjuAsyIeJUvyIBvWxaUuRLIdVd9wzMN5zzLZpDro4; __Secure-1PSIDCC=AP8dLtzP7qJjVmyjGBIFhMi9zCgGQvr-XzgFBWFVBgDZGiBZ9pCNUPUEAMRyvJnSdmQvY1AaFkg; __Secure-3PSIDCC=AP8dLtzXcEu5A9irNbzw_RIvmuYl17Ttga_MaKEllLfZP_DszOQDuSBdDQajHPu4KBrkJpRydWk");
        hashmap.put("origin", "https://console.firebase.google.com");
        return hashmap;
        // revisit logic for cookie and origin
//error : Expected OAuth 2 access token, login cookie or other valid authentication
        //        String accessToken= getAccessToken();
//        hashmap.put("Authentication", "Bearer "+accessToken);
//        System.out.println("hai "+accessToken);
    }

    public HashMap<String, String> getParameterMap() {
        HashMap<String, String> paramMap= new HashMap<>();
        paramMap.put("key", "AIzaSyDovLKo3djdRbs963vqKdbj-geRWyzMTrg");
        paramMap.put("alt", "json");
        return paramMap;
    }

    // get firebase reponse: todo: get firebase response from oauth token to avoid sending cookies

    public String getRecentFirebaseConfig(){
        return apiResponse.getApiResponse("https://firebaseremoteconfig-pa.clients6.google.com", "/v1/projects/1086407581064/entities?", getHeaderMap(),"", getParameterMap()).asString();
    }
    public void publishToFirebase(JSONObject parameter, JSONObject condition) {
        String getRemoteConfig= apiResponse.putAPIResponse("https://firebaseremoteconfig-pa.clients6.google.com", "/v1/projects/1086407581064/entities?", getHeaderMap(), apiUtilities.getJsonData("firebasePutRequest").replace("\"${parameters}\"", parameter.toString()).replace("\"${conditions}\"", condition.toString()), getParameterMap());
    }

    public JSONObject getJsonObjectByTraversingJsonArray(JSONArray jsonArray, String keyInJson, String expectedValue) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);  // this method returns a Object in our array, whose given key value is equal to the expected value
            if(jsonObject.get(keyInJson).equals(expectedValue)) {
                return jsonObject;
            }
        }
        return new JSONObject(); // avoiding NPE
    }

    public JSONObject getMatchingParameter(JSONArray parametersArray, String firebaseKey) {
        return getJsonObjectByTraversingJsonArray(parametersArray, "key", firebaseKey);
    }

    public JSONObject getMatchingCondition(JSONArray conditionsArray, String conditionId) {
        return getJsonObjectByTraversingJsonArray(conditionsArray, "conditionId", conditionId);
    }

    public JSONArray getMatchingParameterAndCondition(String platform, String firebaseKey, String appVersion , String expectedValue) {
//        String firebaseKey= "feature_user_data_collection_enabled";
//        String appVersion= "38.17.5";
//        String platform = "android";
        String response= getRecentFirebaseConfig();
        JsonPath jsonPath= new JsonPath(response);

        JSONArray jsonArray = new JSONArray();

        JSONArray conditionsArray = new JSONArray(jsonPath.getList("conditions"));
        JSONArray parametersArray = new JSONArray(jsonPath.getList("parameters"));

        JSONObject matchingParameter = getMatchingParameter(parametersArray, firebaseKey);
        jsonArray.put(matchingParameter);


        return checkInConditionalValues(conditionsArray, matchingParameter, jsonArray ,platform, appVersion, expectedValue);
    }

    public JSONArray checkInConditionalValues(JSONArray conditionsArray, JSONObject matchingParameter, JSONArray jsonArray, String platform, String appVersion , String expectedValue) {
        if(matchingParameter.has("conditionalValues")) {

            JSONArray conditionalValuesInParameter= matchingParameter.getJSONArray("conditionalValues"); // conditional values array

            for(int j=0; j<conditionalValuesInParameter.length(); j++) { // iterate conditions array from beginning. didn't put this in another func since it has to run till the end to check all conditions
                if(conditionalValuesInParameter.getJSONObject(j).getJSONObject("value").get("value").equals(expectedValue)){ // checking if that conditional value is as per our expected value

                    JSONObject matchingCondition = getMatchingCondition(conditionsArray, conditionalValuesInParameter.getJSONObject(j).get("conditionId").toString());
                    JsonPath jsonPathCondition = new JsonPath(matchingCondition.toString());

                    if(jsonPathCondition.getString("condition.or.conditions[0].and.conditions[0].appId.targetGmpAppId").contains(platform)) { // checking if this condition is for right platform
                        JSONArray targetVersions = new JSONArray(jsonPathCondition.getList("condition.or.conditions[0].and.conditions[1].appVersion.targetAppVersions"));

                        if (checkForConditions(targetVersions, jsonPathCondition.getString("condition.or.conditions[0].and.conditions[1].appVersion.operator"), appVersion)) {
                            jsonArray.put(matchingCondition);
                            return jsonArray;
                        }
                    }
                }
            }
        }
        return jsonArray;
    }

    // checks in condition & default, if its value is as per expectedValue
    @Test
    public void getIsKeyValueAsExpected() { // takes app version, expectedValue(true/false), configKey(make it array/map)

        String expectedValue= "true";
        String firebaseKey= "feature_user_data_collection_enabled";
        String appVersion= "38.17.5";
        String platform = "android";

        JSONArray jsonArray= getMatchingParameterAndCondition(platform, firebaseKey, appVersion,  expectedValue);
        JSONObject parametersArray= jsonArray.getJSONObject(0);
        if(jsonArray.length() < 2) {
            String defaultValue= parametersArray.getJSONObject("defaultValue").getString("value");
            if(defaultValue.equals(expectedValue))
                System.out.println("---->true");
            else
                System.out.println("---->false");
        }

    }

    public Boolean checkForConditions(JSONArray targetVersion, String condition, String appVersion) {
        // add conditions for <=, ==, !=, etc cases
        switch(condition) {
            case "EXACTLY_MATCHES" :
                for(int i=0; i<targetVersion.length(); i++) {
                    System.out.println(targetVersion.get(i));
                    if(targetVersion.get(i).equals(appVersion))
                        return true;
                }
                return false;

            case "CONTAINS" :
                for(int i=0; i<targetVersion.length(); i++) {
                    System.out.println(targetVersion.get(i));
                    if(targetVersion.get(i).toString().contains(appVersion))
                        return true;
                }
                return false;

            case "DOES_NOT_CONTAIN":
                for(int i=0; i<targetVersion.length(); i++) {
                System.out.println(targetVersion.get(i));
                if(targetVersion.get(i).toString().contains(appVersion))
                    return false;
                }
                return true;

            default:
                return false;
        }
    }


    @Test
    public void putAppVersionToExistingConditionAndPublish() { // takes app version, expectedArg(true/false), configKey(make it array/map)

        String firebaseKey = "feature_user_data_collection_enabled";
        String appVersion = "38.17.8";
        String expectedValue = "true";
        String platform = "android";

        JSONArray jsonArray= getMatchingParameterAndCondition(platform, firebaseKey, appVersion, expectedValue);

        JSONObject parameter = jsonArray.getJSONObject(0);
        JSONObject condition = jsonArray.getJSONObject(1);

        String targetValue= condition.getJSONObject("condition").getJSONObject("or").getJSONArray("conditions").getJSONObject(0).getJSONObject("and").getJSONArray("conditions").getJSONObject(1).getJSONObject("appVersion").getString("targetValue");
        targetValue+= ","+appVersion;

        System.out.println("1-->"+condition);

        // editing condition to add our app version
        condition.getJSONObject("condition").getJSONObject("or").getJSONArray("conditions").getJSONObject(0).getJSONObject("and").getJSONArray("conditions").getJSONObject(1).getJSONObject("appVersion").remove("targetValue");
        condition.getJSONObject("condition").getJSONObject("or").getJSONArray("conditions").getJSONObject(0).getJSONObject("and").getJSONArray("conditions").getJSONObject(1).getJSONObject("appVersion").put("targetValue", targetValue);
        condition.getJSONObject("condition").getJSONObject("or").getJSONArray("conditions").getJSONObject(0).getJSONObject("and").getJSONArray("conditions").getJSONObject(1).getJSONObject("appVersion").getJSONArray("targetAppVersions").put(appVersion);

        System.out.println("2-->"+condition);

        publishToFirebase(parameter, condition);

    }

    @Test
    public void rollbackTo() {
        String version = "giveAVersionYouWannaRollBackTo";

        HashMap<String, String> hashMap= new HashMap<>();
        hashMap.put("versionNumber", version);
        String temp= "{\"versionNumber\":\"3540\"}";
        String rollbackResponse= apiResponse.postApiResponse("https://firebaseremoteconfig-pa.clients6.google.com", "/v1/projects/1086407581064/templates:rollback?", getHeaderMap(), temp, getParameterMap());
    }


//    private final static String[] SCOPES = { "https://www.googleapis.com/auth/firebase.remoteconfig", "https://www.googleapis.com/auth/cloud-platform" };
//
//    private static String getAccessToken() throws IOException {
//        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/analyticsproperties/firebase.json"));
//
//        System.out.println(credentials.toString());
//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/analyticsproperties/firebase.json"))
//                .createScoped(Arrays.asList(SCOPES));
//        googleCredentials.refresh();
//        System.out.println(googleCredentials);
//        return googleCredentials.getAccessToken().getTokenValue();
//    }

//    @Test
//    public void getOAuthToken() throws OAuthSystemException, OAuthProblemException {
//
//        //Client id and client secret
//
//
//        OAuthClient client = new OAuthClient(new URLConnectionClient());
//
//        OAuthClientRequest request =
//                OAuthClientRequest.tokenLocation("https://console.firebase.google.com")
//                        .setGrantType(GrantType.CLIENT_CREDENTIALS)
//                        .setClientId("1086407581064-7gpt9oisggjh48lserjil03b4bq64rl8.apps.googleusercontent.com")
//                        .setClientSecret("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")
//                        // .setScope() here if you want to set the token scope
//                        .buildQueryMessage();
//        request.addHeader("Accept", "application/json");
//        request.addHeader("Content-Type", "application/json");
//        request.addHeader("Content-Length", "10000");
//        request.addHeader("content-length", "100000");
//
//        String token = client.accessToken(request, OAuth.HttpMethod.POST, OAuthJSONAccessTokenResponse.class).getAccessToken();
//        System.out.println(token);
//    }



}

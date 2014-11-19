package ac.gre.ma500.mad_sports.models.AppUIModel;

import com.google.gson.Gson;

public class UIModel {
    public SportType[] sportTypes;

    protected String[] getSportTypes() {
        String[] names = new String[sportTypes.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = sportTypes[i].name;
        }
        return names;
    }

    public static UIModel loadFromJson(String json) {
        return new Gson().fromJson(json, UIModel.class);
    }
}

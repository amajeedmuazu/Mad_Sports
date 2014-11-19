package ac.gre.ma500.mad_sports.models.AppUIModel;

public class SportType {
    public String name;
    public SportTeam[] sportTeams;
    public String[] sportActions;
    public int scoreSets;

    protected String[] getSportTeamNames() {
        String[] names = new String[sportTeams.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = sportTeams[i].name;
        }

        return names;
    }

    public SportType() {
        this.scoreSets = 1;
    }
}

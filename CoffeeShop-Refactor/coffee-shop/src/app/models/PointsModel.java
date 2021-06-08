package app.models;

import app.models.EnumBody.*;

public class PointsModel {
    private int points = 0;

    public PointsModel() {
    }

    public PointsModel(int arg) {
        this.action(Points.ADD, arg);
    }


    public void action(Points action, int arg) {
        switch (action) {
            case ADD: {
                this.points += arg;
                break;
            }
            case SUB: {
                this.points -= arg;
                break;
            }
            case SET: {
                this.points = arg;
                break;
            }
        }

    }

    public int action(PointsGet a) {
        return this.points;
    }
}

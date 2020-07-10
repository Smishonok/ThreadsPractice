package com.valentinenikolaev.futureAndPromises;

public class Entity {
    long   typeID;
    String typeName;

    public Entity(long id, String name) {
        this.typeID   = id;
        this.typeName = name;
    }

    public long getTypeID() {
        return typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return "Entity{" + "id=" + typeID + ", name='" + typeName + '\'' + '}';
    }
}

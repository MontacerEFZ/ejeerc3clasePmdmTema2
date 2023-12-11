package montacer.elfazazi.ejeerc3clasepmdmtema2.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import montacer.elfazazi.ejeerc3clasepmdmtema2.modelos.Alumno;

public class AlumnosHelper extends OrmLiteSqliteOpenHelper {

    private Dao<Alumno, Integer>daoAlumnos;

    public AlumnosHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Alumno.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<Alumno, Integer> getDaoAlumnos(){
        if (daoAlumnos == null){
            try {
                daoAlumnos = getDao(Alumno.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return daoAlumnos;
    }
}

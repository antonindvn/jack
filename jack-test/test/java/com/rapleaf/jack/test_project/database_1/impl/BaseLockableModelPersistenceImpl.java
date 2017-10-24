
/**
 * Autogenerated by Jack
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.rapleaf.jack.test_project.database_1.impl;

import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;

import com.rapleaf.jack.AbstractDatabaseModel;
import com.rapleaf.jack.BaseDatabaseConnection;
import com.rapleaf.jack.queries.where_operators.IWhereOperator;
import com.rapleaf.jack.queries.WhereConstraint;
import com.rapleaf.jack.queries.WhereClause;
import com.rapleaf.jack.queries.ModelQuery;
import com.rapleaf.jack.ModelWithId;
import com.rapleaf.jack.util.JackUtility;
import com.rapleaf.jack.test_project.database_1.iface.ILockableModelPersistence;
import com.rapleaf.jack.test_project.database_1.models.LockableModel;
import com.rapleaf.jack.test_project.database_1.query.LockableModelQueryBuilder;
import com.rapleaf.jack.test_project.database_1.query.LockableModelDeleteBuilder;


import com.rapleaf.jack.test_project.IDatabases;

public class BaseLockableModelPersistenceImpl extends AbstractDatabaseModel<LockableModel> implements ILockableModelPersistence {
  private final IDatabases databases;

  public BaseLockableModelPersistenceImpl(BaseDatabaseConnection conn, IDatabases databases) {
    super(conn, "lockable_models", Arrays.<String>asList("lock_version", "message", "created_at", "updated_at"));
    this.databases = databases;
  }

  @Override
  public LockableModel create(Map<Enum, Object> fieldsMap) throws IOException {
    Integer lock_version_tmp = (Integer) fieldsMap.get(LockableModel._Fields.lock_version);
    int lock_version = lock_version_tmp == null ? 0 : lock_version_tmp;
    String message = (String) fieldsMap.get(LockableModel._Fields.message);
    Long created_at = (Long) fieldsMap.get(LockableModel._Fields.created_at);
    Long updated_at = (Long) fieldsMap.get(LockableModel._Fields.updated_at);
    return create(lock_version, message, created_at, updated_at);
  }

  public LockableModel create(final int lock_version, final String message, final Long created_at, final Long updated_at) throws IOException {
    long __id = realCreate(new AttrSetter() {
      public void set(PreparedStatement stmt) throws SQLException {
          stmt.setInt(1, lock_version);
        if (message == null) {
          stmt.setNull(2, java.sql.Types.CHAR);
        } else {
          stmt.setString(2, message);
        }
        if (created_at == null) {
          stmt.setNull(3, java.sql.Types.DATE);
        } else {
          stmt.setTimestamp(3, new Timestamp(created_at));
        }
        if (updated_at == null) {
          stmt.setNull(4, java.sql.Types.DATE);
        } else {
          stmt.setTimestamp(4, new Timestamp(updated_at));
        }
      }
    }, getInsertStatement(Arrays.<String>asList("lock_version", "message", "created_at", "updated_at")));
    LockableModel newInst = new LockableModel(__id, lock_version, message, created_at, updated_at, databases);
    newInst.setCreated(true);
    cachedById.put(__id, newInst);
    clearForeignKeyCache();
    return newInst;
  }


  public LockableModel create(final int lock_version) throws IOException {
    long __id = realCreate(new AttrSetter() {
      public void set(PreparedStatement stmt) throws SQLException {
          stmt.setInt(1, lock_version);
      }
    }, getInsertStatement(Arrays.<String>asList("lock_version")));
    LockableModel newInst = new LockableModel(__id, lock_version, null, null, null, databases);
    newInst.setCreated(true);
    cachedById.put(__id, newInst);
    clearForeignKeyCache();
    return newInst;
  }


  public LockableModel createDefaultInstance() throws IOException {
    return create(0);
  }

  public List<LockableModel> find(Map<Enum, Object> fieldsMap) throws IOException {
    return find(null, fieldsMap);
  }

  public List<LockableModel> find(Set<Long> ids, Map<Enum, Object> fieldsMap) throws IOException {
    List<LockableModel> foundList = new ArrayList<LockableModel>();

    if (fieldsMap == null || fieldsMap.isEmpty()) {
      return foundList;
    }

    StringBuilder statementString = new StringBuilder();
    statementString.append("SELECT * FROM lockable_models WHERE (");
    List<Object> nonNullValues = new ArrayList<Object>();
    List<LockableModel._Fields> nonNullValueFields = new ArrayList<LockableModel._Fields>();

    Iterator<Map.Entry<Enum, Object>> iter = fieldsMap.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry<Enum, Object> entry = iter.next();
      Enum field = entry.getKey();
      Object value = entry.getValue();

      String queryValue = value != null ? " = ? " : " IS NULL";
      if (value != null) {
        nonNullValueFields.add((LockableModel._Fields) field);
        nonNullValues.add(value);
      }

      statementString.append(field).append(queryValue);
      if (iter.hasNext()) {
        statementString.append(" AND ");
      }
    }
    if (ids != null) statementString.append(" AND ").append(getIdSetCondition(ids));
    statementString.append(")");

    int retryCount = 0;
    PreparedStatement preparedStatement;

    while (true) {
      preparedStatement = getPreparedStatement(statementString.toString());

      for (int i = 0; i < nonNullValues.size(); i++) {
        LockableModel._Fields field = nonNullValueFields.get(i);
        try {
          switch (field) {
            case lock_version:
              preparedStatement.setInt(i+1, (Integer) nonNullValues.get(i));
              break;
            case message:
              preparedStatement.setString(i+1, (String) nonNullValues.get(i));
              break;
            case created_at:
              preparedStatement.setTimestamp(i+1, new Timestamp((Long) nonNullValues.get(i)));
              break;
            case updated_at:
              preparedStatement.setTimestamp(i+1, new Timestamp((Long) nonNullValues.get(i)));
              break;
          }
        } catch (SQLException e) {
          throw new IOException(e);
        }
      }

      try {
        executeQuery(foundList, preparedStatement);
        return foundList;
      } catch (SQLRecoverableException e) {
        if (++retryCount > AbstractDatabaseModel.MAX_CONNECTION_RETRIES) {
          throw new IOException(e);
        }
      } catch (SQLException e) {
        throw new IOException(e);
      }
    }
  }

  @Override
  protected void setStatementParameters(PreparedStatement preparedStatement, WhereClause whereClause) throws IOException {
    int index = 0;
    for (WhereConstraint constraint : whereClause.getWhereConstraints()) {
      for (Object parameter : constraint.getParameters()) {
        if (parameter == null) {
          continue;
        }
        try {
          if (constraint.isId()) {
            preparedStatement.setLong(++index, (Long)parameter);
          } else {
            LockableModel._Fields field = (LockableModel._Fields)constraint.getField();
            switch (field) {
              case lock_version:
                preparedStatement.setInt(++index, (Integer) parameter);
                break;
              case message:
                preparedStatement.setString(++index, (String) parameter);
                break;
              case created_at:
                preparedStatement.setTimestamp(++index, new Timestamp((Long) parameter));
                break;
              case updated_at:
                preparedStatement.setTimestamp(++index, new Timestamp((Long) parameter));
                break;
            }
          }
        } catch (SQLException e) {
          throw new IOException(e);
        }
      }
    }
  }

  @Override
  protected void setAttrs(LockableModel model, PreparedStatement stmt, boolean setNull) throws SQLException {
    int index = 1;
    {
      stmt.setInt(index, model.getLockVersion() + 1);
      ++index;
    }
    if (setNull && model.getMessage() == null) {
      stmt.setNull(index, java.sql.Types.CHAR);
      ++index;
    } else if (model.getMessage() != null) {
      stmt.setString(index, model.getMessage());
      ++index;
    }
    if (setNull && model.getCreatedAt() == null) {
      stmt.setNull(index, java.sql.Types.DATE);
      ++index;
    } else if (model.getCreatedAt() != null) {
      stmt.setTimestamp(index, new Timestamp(model.getCreatedAt()));
      ++index;
    }
    if (setNull && model.getUpdatedAt() == null) {
      stmt.setNull(index, java.sql.Types.DATE);
      ++index;
    } else if (model.getUpdatedAt() != null) {
      stmt.setTimestamp(index, new Timestamp(model.getUpdatedAt()));
      ++index;
    }
    stmt.setLong(index, model.getId());
  }

  @Override
  protected LockableModel instanceFromResultSet(ResultSet rs, Set<Enum> selectedFields) throws SQLException {
    boolean allFields = selectedFields == null || selectedFields.isEmpty();
    long id = rs.getLong("id");
    return new LockableModel(id,
      allFields || selectedFields.contains(LockableModel._Fields.lock_version) ? getIntOrNull(rs, "lock_version") : 0,
      allFields || selectedFields.contains(LockableModel._Fields.message) ? rs.getString("message") : null,
      allFields || selectedFields.contains(LockableModel._Fields.created_at) ? getDateAsLong(rs, "created_at") : null,
      allFields || selectedFields.contains(LockableModel._Fields.updated_at) ? getDateAsLong(rs, "updated_at") : null,
      databases
    );
  }

  public List<LockableModel> findByLockVersion(final int value) throws IOException {
    return find(Collections.<Enum, Object>singletonMap(LockableModel._Fields.lock_version, value));
  }

  public List<LockableModel> findByMessage(final String value) throws IOException {
    return find(Collections.<Enum, Object>singletonMap(LockableModel._Fields.message, value));
  }

  public List<LockableModel> findByCreatedAt(final Long value) throws IOException {
    return find(Collections.<Enum, Object>singletonMap(LockableModel._Fields.created_at, value));
  }

  public List<LockableModel> findByUpdatedAt(final Long value) throws IOException {
    return find(Collections.<Enum, Object>singletonMap(LockableModel._Fields.updated_at, value));
  }

  public LockableModelQueryBuilder query() {
    return new LockableModelQueryBuilder(this);
  }

  public LockableModelDeleteBuilder delete() {
    return new LockableModelDeleteBuilder(this);
  }
}

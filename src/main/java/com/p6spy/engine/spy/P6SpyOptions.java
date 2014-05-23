/*
 * #%L
 * P6Spy
 * %%
 * Copyright (C) 2013 P6Spy
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.p6spy.engine.spy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.StandardMBean;

import com.p6spy.engine.logging.P6LogFactory;
import com.p6spy.engine.spy.appender.FileLogger;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.p6spy.engine.spy.appender.P6Logger;
import com.p6spy.engine.spy.appender.SingleLineFormat;
import com.p6spy.engine.spy.option.P6OptionsRepository;

public class P6SpyOptions extends StandardMBean implements P6SpyLoadableOptions {

    public static final String USE_PREFIX = "usePrefix";
    public static final String AUTOFLUSH = "autoflush";
    public static final String DRIVERLIST = "driverlist";
    public static final String DRIVER_NAMES = "driverNames";
    public static final String LOGFILE = "logfile";
    public static final String LOG_MESSAGE_FORMAT = "logMessageFormat";
    public static final String APPEND = "append";
    public static final String DATEFORMAT = "dateformat";
    public static final String APPENDER = "appender";
    public static final String MODULELIST = "modulelist";
    public static final String STACKTRACE = "stacktrace";
    public static final String STACKTRACECLASS = "stacktraceclass";
    public static final String RELOADPROPERTIES = "reloadproperties";
    public static final String RELOADPROPERTIESINTERVAL = "reloadpropertiesinterval";
    public static final String JNDICONTEXTFACTORY = "jndicontextfactory";
    public static final String JNDICONTEXTPROVIDERURL = "jndicontextproviderurl";
    public static final String JNDICONTEXTCUSTOM = "jndicontextcustom";
    public static final String REALDATASOURCE = "realdatasource";
    public static final String REALDATASOURCECLASS = "realdatasourceclass";
    public static final String REALDATASOURCEPROPERTIES = "realdatasourceproperties";
    // TODO not documented!
    public static final String DATABASE_DIALECT_DATE_FORMAT = "databaseDialectDateFormat";
    
    // those set indirectly (via properties visible from outside)
    public static final String MODULE_FACTORIES = "moduleFactories";
    public static final String MODULE_NAMES = "moduleNames";
    public static final String LOG_MESSAGE_FORMAT_INSTANCE = "logMessageFormatInstance";
    public static final String APPENDER_INSTANCE = "appenderInstance";
    
    public static final Map<String, String> defaults;

    static {
      defaults = new HashMap<String, String>();
      defaults.put(LOG_MESSAGE_FORMAT, SingleLineFormat.class.getName());
      defaults.put(LOGFILE, "spy.log");
      defaults.put(APPEND, Boolean.TRUE.toString());
      defaults.put(APPENDER, FileLogger.class.getName());
      defaults.put(MODULELIST, P6SpyFactory.class.getName() + ","+ P6LogFactory.class.getName());
      defaults.put(STACKTRACE, Boolean.FALSE.toString());
      defaults.put(AUTOFLUSH, Boolean.FALSE.toString());
      defaults.put(RELOADPROPERTIES, Boolean.FALSE.toString());
      defaults.put(RELOADPROPERTIESINTERVAL, Long.toString(60));
      defaults.put(DATABASE_DIALECT_DATE_FORMAT, "dd-MMM-yy");
    }

    private final P6OptionsRepository optionsRepository;

    public P6SpyOptions(final P6OptionsRepository optionsRepository) {
      super(P6SpyOptionsMBean.class, false);
      this.optionsRepository = optionsRepository;
    }
    
    @Override
    public void load(Map<String, String> options) {
      setLogMessageFormat(options.get(LOG_MESSAGE_FORMAT));
      setLogfile(options.get(LOGFILE));
      setAppend(options.get(APPEND));
      setDateformat(options.get(DATEFORMAT));
      setAppender(options.get(APPENDER));
      setModulelist(options.get(MODULELIST));
      setDriverlist(options.get(DRIVERLIST));
      setStackTrace(options.get(STACKTRACE));
      setStackTraceClass(options.get(STACKTRACECLASS));
//      setUsePrefix(options.get(USE_PREFIX));
      setAutoflush(options.get(AUTOFLUSH));
      setReloadProperties(options.get(RELOADPROPERTIES));
      setReloadPropertiesInterval(options.get(RELOADPROPERTIESINTERVAL));
      setJNDIContextFactory(options.get(JNDICONTEXTFACTORY));
      setJNDIContextProviderURL(options.get(JNDICONTEXTPROVIDERURL));
      setJNDIContextCustom(options.get(JNDICONTEXTCUSTOM));
      setRealDataSource(options.get(REALDATASOURCE));
      setRealDataSourceClass(options.get(REALDATASOURCECLASS));
      setRealDataSourceProperties(options.get(REALDATASOURCEPROPERTIES));
      setDatabaseDialectDateFormat(options.get(DATABASE_DIALECT_DATE_FORMAT));
    }
    
    /**
     * Utility method, to make accessing options from app less verbose.
     * 
     * @return active instance of the {@link P6SpyLoadableOptions}
     */
    public static P6SpyLoadableOptions getActiveInstance() {
      return P6ModuleManager.getInstance().getOptions(P6SpyOptions.class);
    }

    // JMX exporsed API
    
    @Override
    public void reload() {
      P6ModuleManager.getInstance().reload();
    }
    
    @Override
    public Set<P6Factory> getModuleFactories() {
      return optionsRepository.getSet(P6Factory.class, MODULE_FACTORIES);
    }
    
//    @Override
//    public void setUsePrefix(String usePrefix) {
//      optionsRepository.set(Boolean.class, USE_PREFIX, usePrefix);
//    }
//    
//    @Override
//    public void setUsePrefix(boolean usePrefix) {
//      optionsRepository.set(Boolean.class, USE_PREFIX, usePrefix);
//    }
//
//    @Override
//    public boolean getUsePrefix() {
//      return optionsRepository.get(Boolean.class, USE_PREFIX);
//    }

    @Override
    public void setAutoflush(String autoflush) {
      optionsRepository.set(Boolean.class, AUTOFLUSH, autoflush);
    }
    
    @Override
    public void setAutoflush(boolean autoflush) {
      optionsRepository.set(Boolean.class, AUTOFLUSH, autoflush);
    }

    @Override
    public boolean getAutoflush() {
      return optionsRepository.get(Boolean.class, AUTOFLUSH);
    }

    @Override
    public String getDriverlist() {
      return optionsRepository.get(String.class, DRIVERLIST);
    }

    @Override
    public void setDriverlist(final String driverlist) {
      optionsRepository.set(String.class, DRIVERLIST, driverlist);
      optionsRepository.setSet(String.class, DRIVER_NAMES, driverlist);
    }

    @Override
    public boolean getReloadProperties() {
        return optionsRepository.get(Boolean.class, RELOADPROPERTIES);
    }
    @Override
    public void setReloadProperties(String reloadproperties) {
      optionsRepository.set(Boolean.class, RELOADPROPERTIES, reloadproperties);
    }
    
    @Override
    public void setReloadProperties(boolean reloadproperties) {
      optionsRepository.set(Boolean.class, RELOADPROPERTIES, reloadproperties);
    }
    
    @Override
    public long getReloadPropertiesInterval() {
        return optionsRepository.get(Long.class, RELOADPROPERTIESINTERVAL);
    }
    @Override
    public void setReloadPropertiesInterval(String reloadpropertiesinterval) {
      optionsRepository.set(Long.class, RELOADPROPERTIESINTERVAL, reloadpropertiesinterval);
    }
    
    @Override
    public void setReloadPropertiesInterval(long reloadpropertiesinterval) {
      optionsRepository.set(Long.class, RELOADPROPERTIESINTERVAL, reloadpropertiesinterval);
    }
    
    @Override
    public void setJNDIContextFactory(String jndicontextfactory) {
      optionsRepository.set(String.class, JNDICONTEXTFACTORY, jndicontextfactory);
    }
    @Override
    public String getJNDIContextFactory() {
        return optionsRepository.get(String.class, JNDICONTEXTFACTORY);
    }
    @Override
    public void setJNDIContextProviderURL(String jndicontextproviderurl) {
      optionsRepository.set(String.class, JNDICONTEXTPROVIDERURL, jndicontextproviderurl);
    }
    @Override
    public String getJNDIContextProviderURL() {
        return optionsRepository.get(String.class, JNDICONTEXTPROVIDERURL);
    }
    @Override
    public void setJNDIContextCustom(String jndicontextcustom) {
      optionsRepository.set(String.class, JNDICONTEXTCUSTOM, jndicontextcustom);
    }
    @Override
    public String getJNDIContextCustom() {
        return optionsRepository.get(String.class, JNDICONTEXTCUSTOM);
    }
    @Override
    public void setRealDataSource(String realdatasource) {
      optionsRepository.set(String.class, REALDATASOURCE, realdatasource);
    }
    @Override
    public String getRealDataSource() {
        return optionsRepository.get(String.class, REALDATASOURCE);
    }
    @Override
    public void setRealDataSourceClass(String realdatasourceclass) {
      optionsRepository.set(String.class, REALDATASOURCECLASS, realdatasourceclass);
    }
    @Override
    public String getRealDataSourceClass() {
      return optionsRepository.get(String.class, REALDATASOURCECLASS);
    }
    @Override
    public void setRealDataSourceProperties(String realdatasourceproperties) {
      optionsRepository.set(String.class, REALDATASOURCEPROPERTIES, realdatasourceproperties);
    }
    @Override
    public String getRealDataSourceProperties() {
        return optionsRepository.get(String.class, REALDATASOURCEPROPERTIES);
    }

    @Override
    public Set<String> getDriverNames() {
        return optionsRepository.getSet(String.class, DRIVER_NAMES);
    }

    /**
     * Returns the databaseDialectDateFormat.
     *
     * @return String
     */
    @Override
    public String getDatabaseDialectDateFormat() {
        return optionsRepository.get(String.class, DATABASE_DIALECT_DATE_FORMAT);
    }

    /**
     * Sets the databaseDialectDateFormat.
     *
     * @param databaseDialectDateFormat The databaseDialectDateFormat to set
     */
    @Override
    public void setDatabaseDialectDateFormat(String databaseDialectDateFormat) {
      optionsRepository.set(String.class, DATABASE_DIALECT_DATE_FORMAT, databaseDialectDateFormat);
    }


    @Override
    public String getModulelist() {
      // TODO handle getters for lists represented in csv strings correctly
      return optionsRepository.get(String.class, MODULELIST);
    }

    @Override
    public void setModulelist(String modulelist) {
      // no mather what P6SpyOptions is a must
      if (modulelist != null && modulelist.contains(P6OptionsRepository.COLLECTION_REMOVAL_PREFIX + P6SpyFactory.class.getName())) {
        throw new IllegalArgumentException(P6SpyFactory.class.getName() + " can't be removed from the module list, as it's considered a core factory!");
      }
//      if (modulelist != null && !modulelist.contains(P6SpyFactory.class.getName())) {
//        modulelist += "," + P6SpyFactory.class.getName();
//      }
      
      // TODO handle getters for lists represented in csv strings correctly
      optionsRepository.set(String.class, MODULELIST, modulelist);
      optionsRepository.setSet(String.class, MODULE_NAMES, modulelist);
      optionsRepository.setSet(P6Factory.class, MODULE_FACTORIES, modulelist);
    }      

    @Override
    public Set<String> getModuleNames() {
      return optionsRepository.getSet(String.class, MODULE_NAMES);
    }

    @Override
    public void setAppend(boolean append) {
      optionsRepository.set(Boolean.class, APPEND, append);
    }

    @Override
    public boolean getAppend() {
      return optionsRepository.get(Boolean.class, APPEND);
    }

    @Override
    public String getAppender() {
      return optionsRepository.get(String.class, APPENDER);
    }

    @Override
    public P6Logger getAppenderInstance() {
      return optionsRepository.get(P6Logger.class, APPENDER_INSTANCE);
    }
    
    @Override
    public void setAppender(String className) {
      optionsRepository.set(String.class, APPENDER, className);
      optionsRepository.set(P6Logger.class, APPENDER_INSTANCE, className);
    }

    @Override
    public void setDateformat(String dateformat) {
      optionsRepository.set(String.class, DATEFORMAT, dateformat);
    }

    @Override
    public String getDateformat() {
      return optionsRepository.get(String.class, DATEFORMAT);
    }

    @Override
    public boolean getStackTrace() {
      return optionsRepository.get(Boolean.class, STACKTRACE);
    }

    @Override
    public void setStackTrace(boolean stacktrace) {
      optionsRepository.set(Boolean.class, STACKTRACE, stacktrace);
    }
    
    @Override
    public void setStackTrace(String stacktrace) {
      optionsRepository.set(Boolean.class, STACKTRACE, stacktrace);
    }

    @Override
    public String getStackTraceClass() {
      return optionsRepository.get(String.class, STACKTRACECLASS);
    }

    @Override
    public void setStackTraceClass(String stacktraceclass) {
      optionsRepository.set(String.class, STACKTRACECLASS, stacktraceclass);
    }

    @Override
    public void setLogfile(String logfile) {
      optionsRepository.set(String.class, LOGFILE, logfile);
    }

    @Override
    public String getLogfile() {
      return optionsRepository.get(String.class, LOGFILE);
    }

    @Override
    public void setAppend(String append) {
      optionsRepository.set(Boolean.class, APPEND, append);
    }

    @Override
    public String getLogMessageFormat() {
      return optionsRepository.get(String.class, LOG_MESSAGE_FORMAT);
    }

    @Override
    public void setLogMessageFormat(final String logMessageFormat) {
      optionsRepository.set(String.class, LOG_MESSAGE_FORMAT, logMessageFormat);
      optionsRepository.set(MessageFormattingStrategy.class, LOG_MESSAGE_FORMAT_INSTANCE, logMessageFormat);
    }

    @Override
    public MessageFormattingStrategy getLogMessageFormatInstance() {
      return optionsRepository.get(MessageFormattingStrategy.class, LOG_MESSAGE_FORMAT_INSTANCE);
    }
    
    @Override
    public Map<String, String> getDefaults() {
      return defaults;
    }

}
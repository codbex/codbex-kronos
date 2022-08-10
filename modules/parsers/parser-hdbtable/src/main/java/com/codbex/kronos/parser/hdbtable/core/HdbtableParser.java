/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
// Generated from com/codbex/kronos/parser/hdbtable/core/Hdbtable.g4 by ANTLR 4.10.1
package com.codbex.kronos.parser.hdbtable.core;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * The Class HdbtableParser.
 */
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HdbtableParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	/** The Constant _decisionToDFA. */
	protected static final DFA[] _decisionToDFA;
	
	/** The Constant _sharedContextCache. */
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	
	/** The Constant COMMENT. */
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, STRING=28, WS=29, TABLE=30, DOT=31, EQ=32, 
		SEMICOLON=33, SQLTYPES=34, BOOLEAN=35, ORDER=36, INDEXTYPE=37, INT=38, 
		TABLETYPE=39, TABLELOGGINGTYPE=40, DATETIMEDEFAULTVALUES=41, LINE_COMMENT=42, 
		COMMENT=43;
	
	/** The Constant RULE_indexColumnsArray. */
	public static final int
		RULE_hdbtableDefinition = 0, RULE_hdbtableProperties = 1, RULE_schemaNameProp = 2, 
		RULE_temporaryProp = 3, RULE_tableTypeProp = 4, RULE_publicProp = 5, RULE_loggingTypeProp = 6, 
		RULE_tableColumnsProp = 7, RULE_tableIndexesProp = 8, RULE_tablePrimaryKeyProp = 9, 
		RULE_tablePrimaryKeyColumnsProp = 10, RULE_tablePrimaryKeyIndexTypeProp = 11, 
		RULE_descriptionProp = 12, RULE_columnsObject = 13, RULE_columnsProperties = 14, 
		RULE_indexesObject = 15, RULE_indexProperties = 16, RULE_columnAssignName = 17, 
		RULE_columnAssignSQLType = 18, RULE_columnAssignNullable = 19, RULE_columnAssignUnique = 20, 
		RULE_columnAssignLength = 21, RULE_columnAssignComment = 22, RULE_columnAssignDefaultValue = 23, 
		RULE_columnAssignPrecision = 24, RULE_columnAssignScale = 25, RULE_indexAssignName = 26, 
		RULE_indexAssignUnique = 27, RULE_indexAssignOrder = 28, RULE_indexAssignIndexColumns = 29, 
		RULE_indexAssignIndexType = 30, RULE_indexColumnsArray = 31;
	
	/**
	 * Make rule names.
	 *
	 * @return the string[]
	 */
	private static String[] makeRuleNames() {
		return new String[] {
			"hdbtableDefinition", "hdbtableProperties", "schemaNameProp", "temporaryProp", 
			"tableTypeProp", "publicProp", "loggingTypeProp", "tableColumnsProp", 
			"tableIndexesProp", "tablePrimaryKeyProp", "tablePrimaryKeyColumnsProp", 
			"tablePrimaryKeyIndexTypeProp", "descriptionProp", "columnsObject", "columnsProperties", 
			"indexesObject", "indexProperties", "columnAssignName", "columnAssignSQLType", 
			"columnAssignNullable", "columnAssignUnique", "columnAssignLength", "columnAssignComment", 
			"columnAssignDefaultValue", "columnAssignPrecision", "columnAssignScale", 
			"indexAssignName", "indexAssignUnique", "indexAssignOrder", "indexAssignIndexColumns", 
			"indexAssignIndexType", "indexColumnsArray"
		};
	}
	
	/** The Constant ruleNames. */
	public static final String[] ruleNames = makeRuleNames();

	/**
	 * Make literal names.
	 *
	 * @return the string[]
	 */
	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'schemaName'", "'temporary'", "'tableType'", "'public'", "'loggingType'", 
			"'columns'", "'['", "','", "']'", "'indexes'", "'primaryKey'", "'pkcolumns'", 
			"'indexType'", "'description'", "'{'", "'}'", "'name'", "'sqlType'", 
			"'nullable'", "'unique'", "'length'", "'comment'", "'defaultValue'", 
			"'precision'", "'scale'", "'order'", "'indexColumns'", null, null, "'table'", 
			"'.'", "'='", "';'"
		};
	}
	
	/** The Constant _LITERAL_NAMES. */
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	
	/**
	 * Make symbolic names.
	 *
	 * @return the string[]
	 */
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "STRING", "WS", "TABLE", "DOT", "EQ", "SEMICOLON", 
			"SQLTYPES", "BOOLEAN", "ORDER", "INDEXTYPE", "INT", "TABLETYPE", "TABLELOGGINGTYPE", 
			"DATETIMEDEFAULTVALUES", "LINE_COMMENT", "COMMENT"
		};
	}
	
	/** The Constant _SYMBOLIC_NAMES. */
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	
	/** The Constant VOCABULARY. */
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * The Constant tokenNames.
	 *
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	/**
	 * Gets the token names.
	 *
	 * @return the token names
	 */
	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	/**
	 * Gets the vocabulary.
	 *
	 * @return the vocabulary
	 */
	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	/**
	 * Gets the grammar file name.
	 *
	 * @return the grammar file name
	 */
	@Override
	public String getGrammarFileName() { return "Hdbtable.g4"; }

	/**
	 * Gets the rule names.
	 *
	 * @return the rule names
	 */
	@Override
	public String[] getRuleNames() { return ruleNames; }

	/**
	 * Gets the serialized ATN.
	 *
	 * @return the serialized ATN
	 */
	@Override
	public String getSerializedATN() { return _serializedATN; }

	/**
	 * Gets the atn.
	 *
	 * @return the atn
	 */
	@Override
	public ATN getATN() { return _ATN; }

	/**
	 * Instantiates a new hdbtable parser.
	 *
	 * @param input the input
	 */
	public HdbtableParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	/**
	 * The Class HdbtableDefinitionContext.
	 */
	public static class HdbtableDefinitionContext extends ParserRuleContext {
		
		/**
		 * Hdbtable properties.
		 *
		 * @return the list
		 */
		public List<HdbtablePropertiesContext> hdbtableProperties() {
			return getRuleContexts(HdbtablePropertiesContext.class);
		}
		
		/**
		 * Hdbtable properties.
		 *
		 * @param i the i
		 * @return the hdbtable properties context
		 */
		public HdbtablePropertiesContext hdbtableProperties(int i) {
			return getRuleContext(HdbtablePropertiesContext.class,i);
		}
		
		/**
		 * Instantiates a new hdbtable definition context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public HdbtableDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_hdbtableDefinition; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterHdbtableDefinition(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitHdbtableDefinition(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitHdbtableDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Hdbtable definition.
	 *
	 * @return the hdbtable definition context
	 * @throws RecognitionException the recognition exception
	 */
	public final HdbtableDefinitionContext hdbtableDefinition() throws RecognitionException {
		HdbtableDefinitionContext _localctx = new HdbtableDefinitionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_hdbtableDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(64);
				hdbtableProperties();
				}
				}
				setState(67); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TABLE );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class HdbtablePropertiesContext.
	 */
	public static class HdbtablePropertiesContext extends ParserRuleContext {
		
		/**
		 * Schema name prop.
		 *
		 * @return the schema name prop context
		 */
		public SchemaNamePropContext schemaNameProp() {
			return getRuleContext(SchemaNamePropContext.class,0);
		}
		
		/**
		 * Temporary prop.
		 *
		 * @return the temporary prop context
		 */
		public TemporaryPropContext temporaryProp() {
			return getRuleContext(TemporaryPropContext.class,0);
		}
		
		/**
		 * Table type prop.
		 *
		 * @return the table type prop context
		 */
		public TableTypePropContext tableTypeProp() {
			return getRuleContext(TableTypePropContext.class,0);
		}
		
		/**
		 * Public prop.
		 *
		 * @return the public prop context
		 */
		public PublicPropContext publicProp() {
			return getRuleContext(PublicPropContext.class,0);
		}
		
		/**
		 * Logging type prop.
		 *
		 * @return the logging type prop context
		 */
		public LoggingTypePropContext loggingTypeProp() {
			return getRuleContext(LoggingTypePropContext.class,0);
		}
		
		/**
		 * Table columns prop.
		 *
		 * @return the table columns prop context
		 */
		public TableColumnsPropContext tableColumnsProp() {
			return getRuleContext(TableColumnsPropContext.class,0);
		}
		
		/**
		 * Table indexes prop.
		 *
		 * @return the table indexes prop context
		 */
		public TableIndexesPropContext tableIndexesProp() {
			return getRuleContext(TableIndexesPropContext.class,0);
		}
		
		/**
		 * Table primary key prop.
		 *
		 * @return the table primary key prop context
		 */
		public TablePrimaryKeyPropContext tablePrimaryKeyProp() {
			return getRuleContext(TablePrimaryKeyPropContext.class,0);
		}
		
		/**
		 * Table primary key index type prop.
		 *
		 * @return the table primary key index type prop context
		 */
		public TablePrimaryKeyIndexTypePropContext tablePrimaryKeyIndexTypeProp() {
			return getRuleContext(TablePrimaryKeyIndexTypePropContext.class,0);
		}
		
		/**
		 * Description prop.
		 *
		 * @return the description prop context
		 */
		public DescriptionPropContext descriptionProp() {
			return getRuleContext(DescriptionPropContext.class,0);
		}
		
		/**
		 * Instantiates a new hdbtable properties context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public HdbtablePropertiesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_hdbtableProperties; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterHdbtableProperties(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitHdbtableProperties(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitHdbtableProperties(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Hdbtable properties.
	 *
	 * @return the hdbtable properties context
	 * @throws RecognitionException the recognition exception
	 */
	public final HdbtablePropertiesContext hdbtableProperties() throws RecognitionException {
		HdbtablePropertiesContext _localctx = new HdbtablePropertiesContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_hdbtableProperties);
		try {
			setState(79);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(69);
				schemaNameProp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(70);
				temporaryProp();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(71);
				tableTypeProp();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(72);
				publicProp();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(73);
				loggingTypeProp();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(74);
				tableColumnsProp();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(75);
				tableIndexesProp();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(76);
				tablePrimaryKeyProp();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(77);
				tablePrimaryKeyIndexTypeProp();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(78);
				descriptionProp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class SchemaNamePropContext.
	 */
	public static class SchemaNamePropContext extends ParserRuleContext {
		
		/**
		 * Table.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLE() { return getToken(HdbtableParser.TABLE, 0); }
		
		/**
		 * Dot.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DOT() { return getToken(HdbtableParser.DOT, 0); }
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(HdbtableParser.STRING, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new schema name prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public SchemaNamePropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_schemaNameProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterSchemaNameProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitSchemaNameProp(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitSchemaNameProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Schema name prop.
	 *
	 * @return the schema name prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final SchemaNamePropContext schemaNameProp() throws RecognitionException {
		SchemaNamePropContext _localctx = new SchemaNamePropContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_schemaNameProp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(TABLE);
			setState(82);
			match(DOT);
			setState(83);
			match(T__0);
			setState(84);
			match(EQ);
			setState(85);
			match(STRING);
			setState(86);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class TemporaryPropContext.
	 */
	public static class TemporaryPropContext extends ParserRuleContext {
		
		/**
		 * Table.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLE() { return getToken(HdbtableParser.TABLE, 0); }
		
		/**
		 * Dot.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DOT() { return getToken(HdbtableParser.DOT, 0); }
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Boolean.
		 *
		 * @return the terminal node
		 */
		public TerminalNode BOOLEAN() { return getToken(HdbtableParser.BOOLEAN, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new temporary prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public TemporaryPropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_temporaryProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterTemporaryProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitTemporaryProp(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitTemporaryProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Temporary prop.
	 *
	 * @return the temporary prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final TemporaryPropContext temporaryProp() throws RecognitionException {
		TemporaryPropContext _localctx = new TemporaryPropContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_temporaryProp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(TABLE);
			setState(89);
			match(DOT);
			setState(90);
			match(T__1);
			setState(91);
			match(EQ);
			setState(92);
			match(BOOLEAN);
			setState(93);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class TableTypePropContext.
	 */
	public static class TableTypePropContext extends ParserRuleContext {
		
		/**
		 * Table.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLE() { return getToken(HdbtableParser.TABLE, 0); }
		
		/**
		 * Dot.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DOT() { return getToken(HdbtableParser.DOT, 0); }
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Tabletype.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLETYPE() { return getToken(HdbtableParser.TABLETYPE, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new table type prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public TableTypePropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_tableTypeProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterTableTypeProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitTableTypeProp(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitTableTypeProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Table type prop.
	 *
	 * @return the table type prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final TableTypePropContext tableTypeProp() throws RecognitionException {
		TableTypePropContext _localctx = new TableTypePropContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_tableTypeProp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(TABLE);
			setState(96);
			match(DOT);
			setState(97);
			match(T__2);
			setState(98);
			match(EQ);
			setState(99);
			match(TABLETYPE);
			setState(100);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class PublicPropContext.
	 */
	public static class PublicPropContext extends ParserRuleContext {
		
		/**
		 * Table.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLE() { return getToken(HdbtableParser.TABLE, 0); }
		
		/**
		 * Dot.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DOT() { return getToken(HdbtableParser.DOT, 0); }
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Boolean.
		 *
		 * @return the terminal node
		 */
		public TerminalNode BOOLEAN() { return getToken(HdbtableParser.BOOLEAN, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new public prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public PublicPropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_publicProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterPublicProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitPublicProp(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitPublicProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Public prop.
	 *
	 * @return the public prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final PublicPropContext publicProp() throws RecognitionException {
		PublicPropContext _localctx = new PublicPropContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_publicProp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(TABLE);
			setState(103);
			match(DOT);
			setState(104);
			match(T__3);
			setState(105);
			match(EQ);
			setState(106);
			match(BOOLEAN);
			setState(107);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class LoggingTypePropContext.
	 */
	public static class LoggingTypePropContext extends ParserRuleContext {
		
		/**
		 * Table.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLE() { return getToken(HdbtableParser.TABLE, 0); }
		
		/**
		 * Dot.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DOT() { return getToken(HdbtableParser.DOT, 0); }
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Tableloggingtype.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLELOGGINGTYPE() { return getToken(HdbtableParser.TABLELOGGINGTYPE, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new logging type prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public LoggingTypePropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_loggingTypeProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterLoggingTypeProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitLoggingTypeProp(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitLoggingTypeProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Logging type prop.
	 *
	 * @return the logging type prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final LoggingTypePropContext loggingTypeProp() throws RecognitionException {
		LoggingTypePropContext _localctx = new LoggingTypePropContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_loggingTypeProp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(TABLE);
			setState(110);
			match(DOT);
			setState(111);
			match(T__4);
			setState(112);
			match(EQ);
			setState(113);
			match(TABLELOGGINGTYPE);
			setState(114);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class TableColumnsPropContext.
	 */
	public static class TableColumnsPropContext extends ParserRuleContext {
		
		/**
		 * Table.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLE() { return getToken(HdbtableParser.TABLE, 0); }
		
		/**
		 * Dot.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DOT() { return getToken(HdbtableParser.DOT, 0); }
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Columns object.
		 *
		 * @return the list
		 */
		public List<ColumnsObjectContext> columnsObject() {
			return getRuleContexts(ColumnsObjectContext.class);
		}
		
		/**
		 * Columns object.
		 *
		 * @param i the i
		 * @return the columns object context
		 */
		public ColumnsObjectContext columnsObject(int i) {
			return getRuleContext(ColumnsObjectContext.class,i);
		}
		
		/**
		 * Instantiates a new table columns prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public TableColumnsPropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_tableColumnsProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterTableColumnsProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitTableColumnsProp(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitTableColumnsProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Table columns prop.
	 *
	 * @return the table columns prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final TableColumnsPropContext tableColumnsProp() throws RecognitionException {
		TableColumnsPropContext _localctx = new TableColumnsPropContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_tableColumnsProp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			match(TABLE);
			setState(117);
			match(DOT);
			setState(118);
			match(T__5);
			setState(119);
			match(EQ);
			setState(120);
			match(T__6);
			setState(129);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__14) {
				{
				setState(121);
				columnsObject();
				setState(126);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(122);
					match(T__7);
					setState(123);
					columnsObject();
					}
					}
					setState(128);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(131);
			match(T__8);
			setState(132);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class TableIndexesPropContext.
	 */
	public static class TableIndexesPropContext extends ParserRuleContext {
		
		/**
		 * Table.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLE() { return getToken(HdbtableParser.TABLE, 0); }
		
		/**
		 * Dot.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DOT() { return getToken(HdbtableParser.DOT, 0); }
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Indexes object.
		 *
		 * @return the list
		 */
		public List<IndexesObjectContext> indexesObject() {
			return getRuleContexts(IndexesObjectContext.class);
		}
		
		/**
		 * Indexes object.
		 *
		 * @param i the i
		 * @return the indexes object context
		 */
		public IndexesObjectContext indexesObject(int i) {
			return getRuleContext(IndexesObjectContext.class,i);
		}
		
		/**
		 * Instantiates a new table indexes prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public TableIndexesPropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_tableIndexesProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterTableIndexesProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitTableIndexesProp(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitTableIndexesProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Table indexes prop.
	 *
	 * @return the table indexes prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final TableIndexesPropContext tableIndexesProp() throws RecognitionException {
		TableIndexesPropContext _localctx = new TableIndexesPropContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_tableIndexesProp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(TABLE);
			setState(135);
			match(DOT);
			setState(136);
			match(T__9);
			setState(137);
			match(EQ);
			setState(138);
			match(T__6);
			setState(147);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__14) {
				{
				setState(139);
				indexesObject();
				setState(144);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(140);
					match(T__7);
					setState(141);
					indexesObject();
					}
					}
					setState(146);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(149);
			match(T__8);
			setState(150);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class TablePrimaryKeyPropContext.
	 */
	public static class TablePrimaryKeyPropContext extends ParserRuleContext {
		
		/**
		 * Table.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLE() { return getToken(HdbtableParser.TABLE, 0); }
		
		/**
		 * Dot.
		 *
		 * @return the list
		 */
		public List<TerminalNode> DOT() { return getTokens(HdbtableParser.DOT); }
		
		/**
		 * Dot.
		 *
		 * @param i the i
		 * @return the terminal node
		 */
		public TerminalNode DOT(int i) {
			return getToken(HdbtableParser.DOT, i);
		}
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Table primary key columns prop.
		 *
		 * @return the table primary key columns prop context
		 */
		public TablePrimaryKeyColumnsPropContext tablePrimaryKeyColumnsProp() {
			return getRuleContext(TablePrimaryKeyColumnsPropContext.class,0);
		}
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new table primary key prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public TablePrimaryKeyPropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_tablePrimaryKeyProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterTablePrimaryKeyProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitTablePrimaryKeyProp(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitTablePrimaryKeyProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Table primary key prop.
	 *
	 * @return the table primary key prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final TablePrimaryKeyPropContext tablePrimaryKeyProp() throws RecognitionException {
		TablePrimaryKeyPropContext _localctx = new TablePrimaryKeyPropContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_tablePrimaryKeyProp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			match(TABLE);
			setState(153);
			match(DOT);
			setState(154);
			match(T__10);
			setState(155);
			match(DOT);
			setState(156);
			match(T__11);
			setState(157);
			match(EQ);
			setState(158);
			tablePrimaryKeyColumnsProp();
			setState(159);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class TablePrimaryKeyColumnsPropContext.
	 */
	public static class TablePrimaryKeyColumnsPropContext extends ParserRuleContext {
		
		/**
		 * String.
		 *
		 * @return the list
		 */
		public List<TerminalNode> STRING() { return getTokens(HdbtableParser.STRING); }
		
		/**
		 * String.
		 *
		 * @param i the i
		 * @return the terminal node
		 */
		public TerminalNode STRING(int i) {
			return getToken(HdbtableParser.STRING, i);
		}
		
		/**
		 * Instantiates a new table primary key columns prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public TablePrimaryKeyColumnsPropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_tablePrimaryKeyColumnsProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterTablePrimaryKeyColumnsProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitTablePrimaryKeyColumnsProp(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitTablePrimaryKeyColumnsProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Table primary key columns prop.
	 *
	 * @return the table primary key columns prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final TablePrimaryKeyColumnsPropContext tablePrimaryKeyColumnsProp() throws RecognitionException {
		TablePrimaryKeyColumnsPropContext _localctx = new TablePrimaryKeyColumnsPropContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_tablePrimaryKeyColumnsProp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			match(T__6);
			setState(162);
			match(STRING);
			setState(167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(163);
				match(T__7);
				setState(164);
				match(STRING);
				}
				}
				setState(169);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(170);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class TablePrimaryKeyIndexTypePropContext.
	 */
	public static class TablePrimaryKeyIndexTypePropContext extends ParserRuleContext {
		
		/**
		 * Table.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLE() { return getToken(HdbtableParser.TABLE, 0); }
		
		/**
		 * Dot.
		 *
		 * @return the list
		 */
		public List<TerminalNode> DOT() { return getTokens(HdbtableParser.DOT); }
		
		/**
		 * Dot.
		 *
		 * @param i the i
		 * @return the terminal node
		 */
		public TerminalNode DOT(int i) {
			return getToken(HdbtableParser.DOT, i);
		}
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Indextype.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INDEXTYPE() { return getToken(HdbtableParser.INDEXTYPE, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new table primary key index type prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public TablePrimaryKeyIndexTypePropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_tablePrimaryKeyIndexTypeProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterTablePrimaryKeyIndexTypeProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitTablePrimaryKeyIndexTypeProp(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitTablePrimaryKeyIndexTypeProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Table primary key index type prop.
	 *
	 * @return the table primary key index type prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final TablePrimaryKeyIndexTypePropContext tablePrimaryKeyIndexTypeProp() throws RecognitionException {
		TablePrimaryKeyIndexTypePropContext _localctx = new TablePrimaryKeyIndexTypePropContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_tablePrimaryKeyIndexTypeProp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(TABLE);
			setState(173);
			match(DOT);
			setState(174);
			match(T__10);
			setState(175);
			match(DOT);
			setState(176);
			match(T__12);
			setState(177);
			match(EQ);
			setState(178);
			match(INDEXTYPE);
			setState(179);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class DescriptionPropContext.
	 */
	public static class DescriptionPropContext extends ParserRuleContext {
		
		/**
		 * Table.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TABLE() { return getToken(HdbtableParser.TABLE, 0); }
		
		/**
		 * Dot.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DOT() { return getToken(HdbtableParser.DOT, 0); }
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(HdbtableParser.STRING, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new description prop context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public DescriptionPropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_descriptionProp; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterDescriptionProp(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitDescriptionProp(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitDescriptionProp(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Description prop.
	 *
	 * @return the description prop context
	 * @throws RecognitionException the recognition exception
	 */
	public final DescriptionPropContext descriptionProp() throws RecognitionException {
		DescriptionPropContext _localctx = new DescriptionPropContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_descriptionProp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			match(TABLE);
			setState(182);
			match(DOT);
			setState(183);
			match(T__13);
			setState(184);
			match(EQ);
			setState(185);
			match(STRING);
			setState(186);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class ColumnsObjectContext.
	 */
	public static class ColumnsObjectContext extends ParserRuleContext {
		
		/**
		 * Columns properties.
		 *
		 * @return the list
		 */
		public List<ColumnsPropertiesContext> columnsProperties() {
			return getRuleContexts(ColumnsPropertiesContext.class);
		}
		
		/**
		 * Columns properties.
		 *
		 * @param i the i
		 * @return the columns properties context
		 */
		public ColumnsPropertiesContext columnsProperties(int i) {
			return getRuleContext(ColumnsPropertiesContext.class,i);
		}
		
		/**
		 * Instantiates a new columns object context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ColumnsObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_columnsObject; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterColumnsObject(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitColumnsObject(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitColumnsObject(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Columns object.
	 *
	 * @return the columns object context
	 * @throws RecognitionException the recognition exception
	 */
	public final ColumnsObjectContext columnsObject() throws RecognitionException {
		ColumnsObjectContext _localctx = new ColumnsObjectContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_columnsObject);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(T__14);
			setState(190); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(189);
				columnsProperties();
				}
				}
				setState(192); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24))) != 0) );
			setState(194);
			match(T__15);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class ColumnsPropertiesContext.
	 */
	public static class ColumnsPropertiesContext extends ParserRuleContext {
		
		/**
		 * Column assign name.
		 *
		 * @return the column assign name context
		 */
		public ColumnAssignNameContext columnAssignName() {
			return getRuleContext(ColumnAssignNameContext.class,0);
		}
		
		/**
		 * Column assign SQL type.
		 *
		 * @return the column assign SQL type context
		 */
		public ColumnAssignSQLTypeContext columnAssignSQLType() {
			return getRuleContext(ColumnAssignSQLTypeContext.class,0);
		}
		
		/**
		 * Column assign unique.
		 *
		 * @return the column assign unique context
		 */
		public ColumnAssignUniqueContext columnAssignUnique() {
			return getRuleContext(ColumnAssignUniqueContext.class,0);
		}
		
		/**
		 * Column assign length.
		 *
		 * @return the column assign length context
		 */
		public ColumnAssignLengthContext columnAssignLength() {
			return getRuleContext(ColumnAssignLengthContext.class,0);
		}
		
		/**
		 * Column assign nullable.
		 *
		 * @return the column assign nullable context
		 */
		public ColumnAssignNullableContext columnAssignNullable() {
			return getRuleContext(ColumnAssignNullableContext.class,0);
		}
		
		/**
		 * Column assign comment.
		 *
		 * @return the column assign comment context
		 */
		public ColumnAssignCommentContext columnAssignComment() {
			return getRuleContext(ColumnAssignCommentContext.class,0);
		}
		
		/**
		 * Column assign default value.
		 *
		 * @return the column assign default value context
		 */
		public ColumnAssignDefaultValueContext columnAssignDefaultValue() {
			return getRuleContext(ColumnAssignDefaultValueContext.class,0);
		}
		
		/**
		 * Column assign precision.
		 *
		 * @return the column assign precision context
		 */
		public ColumnAssignPrecisionContext columnAssignPrecision() {
			return getRuleContext(ColumnAssignPrecisionContext.class,0);
		}
		
		/**
		 * Column assign scale.
		 *
		 * @return the column assign scale context
		 */
		public ColumnAssignScaleContext columnAssignScale() {
			return getRuleContext(ColumnAssignScaleContext.class,0);
		}
		
		/**
		 * Instantiates a new columns properties context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ColumnsPropertiesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_columnsProperties; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterColumnsProperties(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitColumnsProperties(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitColumnsProperties(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Columns properties.
	 *
	 * @return the columns properties context
	 * @throws RecognitionException the recognition exception
	 */
	public final ColumnsPropertiesContext columnsProperties() throws RecognitionException {
		ColumnsPropertiesContext _localctx = new ColumnsPropertiesContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_columnsProperties);
		try {
			setState(205);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__16:
				enterOuterAlt(_localctx, 1);
				{
				setState(196);
				columnAssignName();
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 2);
				{
				setState(197);
				columnAssignSQLType();
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 3);
				{
				setState(198);
				columnAssignUnique();
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 4);
				{
				setState(199);
				columnAssignLength();
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 5);
				{
				setState(200);
				columnAssignNullable();
				}
				break;
			case T__21:
				enterOuterAlt(_localctx, 6);
				{
				setState(201);
				columnAssignComment();
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 7);
				{
				setState(202);
				columnAssignDefaultValue();
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 8);
				{
				setState(203);
				columnAssignPrecision();
				}
				break;
			case T__24:
				enterOuterAlt(_localctx, 9);
				{
				setState(204);
				columnAssignScale();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class IndexesObjectContext.
	 */
	public static class IndexesObjectContext extends ParserRuleContext {
		
		/**
		 * Index properties.
		 *
		 * @return the list
		 */
		public List<IndexPropertiesContext> indexProperties() {
			return getRuleContexts(IndexPropertiesContext.class);
		}
		
		/**
		 * Index properties.
		 *
		 * @param i the i
		 * @return the index properties context
		 */
		public IndexPropertiesContext indexProperties(int i) {
			return getRuleContext(IndexPropertiesContext.class,i);
		}
		
		/**
		 * Instantiates a new indexes object context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public IndexesObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_indexesObject; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterIndexesObject(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitIndexesObject(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitIndexesObject(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Indexes object.
	 *
	 * @return the indexes object context
	 * @throws RecognitionException the recognition exception
	 */
	public final IndexesObjectContext indexesObject() throws RecognitionException {
		IndexesObjectContext _localctx = new IndexesObjectContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_indexesObject);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			match(T__14);
			setState(209); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(208);
				indexProperties();
				}
				}
				setState(211); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__16) | (1L << T__19) | (1L << T__25) | (1L << T__26))) != 0) );
			setState(213);
			match(T__15);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class IndexPropertiesContext.
	 */
	public static class IndexPropertiesContext extends ParserRuleContext {
		
		/**
		 * Index assign name.
		 *
		 * @return the index assign name context
		 */
		public IndexAssignNameContext indexAssignName() {
			return getRuleContext(IndexAssignNameContext.class,0);
		}
		
		/**
		 * Index assign unique.
		 *
		 * @return the index assign unique context
		 */
		public IndexAssignUniqueContext indexAssignUnique() {
			return getRuleContext(IndexAssignUniqueContext.class,0);
		}
		
		/**
		 * Index assign order.
		 *
		 * @return the index assign order context
		 */
		public IndexAssignOrderContext indexAssignOrder() {
			return getRuleContext(IndexAssignOrderContext.class,0);
		}
		
		/**
		 * Index assign index columns.
		 *
		 * @return the index assign index columns context
		 */
		public IndexAssignIndexColumnsContext indexAssignIndexColumns() {
			return getRuleContext(IndexAssignIndexColumnsContext.class,0);
		}
		
		/**
		 * Index assign index type.
		 *
		 * @return the index assign index type context
		 */
		public IndexAssignIndexTypeContext indexAssignIndexType() {
			return getRuleContext(IndexAssignIndexTypeContext.class,0);
		}
		
		/**
		 * Instantiates a new index properties context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public IndexPropertiesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_indexProperties; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterIndexProperties(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitIndexProperties(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitIndexProperties(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Index properties.
	 *
	 * @return the index properties context
	 * @throws RecognitionException the recognition exception
	 */
	public final IndexPropertiesContext indexProperties() throws RecognitionException {
		IndexPropertiesContext _localctx = new IndexPropertiesContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_indexProperties);
		try {
			setState(220);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__16:
				enterOuterAlt(_localctx, 1);
				{
				setState(215);
				indexAssignName();
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 2);
				{
				setState(216);
				indexAssignUnique();
				}
				break;
			case T__25:
				enterOuterAlt(_localctx, 3);
				{
				setState(217);
				indexAssignOrder();
				}
				break;
			case T__26:
				enterOuterAlt(_localctx, 4);
				{
				setState(218);
				indexAssignIndexColumns();
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 5);
				{
				setState(219);
				indexAssignIndexType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class ColumnAssignNameContext.
	 */
	public static class ColumnAssignNameContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(HdbtableParser.STRING, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new column assign name context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ColumnAssignNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_columnAssignName; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterColumnAssignName(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitColumnAssignName(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitColumnAssignName(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Column assign name.
	 *
	 * @return the column assign name context
	 * @throws RecognitionException the recognition exception
	 */
	public final ColumnAssignNameContext columnAssignName() throws RecognitionException {
		ColumnAssignNameContext _localctx = new ColumnAssignNameContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_columnAssignName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(T__16);
			setState(223);
			match(EQ);
			setState(224);
			match(STRING);
			setState(225);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class ColumnAssignSQLTypeContext.
	 */
	public static class ColumnAssignSQLTypeContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Sqltypes.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SQLTYPES() { return getToken(HdbtableParser.SQLTYPES, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new column assign SQL type context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ColumnAssignSQLTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_columnAssignSQLType; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterColumnAssignSQLType(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitColumnAssignSQLType(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitColumnAssignSQLType(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Column assign SQL type.
	 *
	 * @return the column assign SQL type context
	 * @throws RecognitionException the recognition exception
	 */
	public final ColumnAssignSQLTypeContext columnAssignSQLType() throws RecognitionException {
		ColumnAssignSQLTypeContext _localctx = new ColumnAssignSQLTypeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_columnAssignSQLType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			match(T__17);
			setState(228);
			match(EQ);
			setState(229);
			match(SQLTYPES);
			setState(230);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class ColumnAssignNullableContext.
	 */
	public static class ColumnAssignNullableContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Boolean.
		 *
		 * @return the terminal node
		 */
		public TerminalNode BOOLEAN() { return getToken(HdbtableParser.BOOLEAN, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new column assign nullable context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ColumnAssignNullableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_columnAssignNullable; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterColumnAssignNullable(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitColumnAssignNullable(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitColumnAssignNullable(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Column assign nullable.
	 *
	 * @return the column assign nullable context
	 * @throws RecognitionException the recognition exception
	 */
	public final ColumnAssignNullableContext columnAssignNullable() throws RecognitionException {
		ColumnAssignNullableContext _localctx = new ColumnAssignNullableContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_columnAssignNullable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(T__18);
			setState(233);
			match(EQ);
			setState(234);
			match(BOOLEAN);
			setState(235);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class ColumnAssignUniqueContext.
	 */
	public static class ColumnAssignUniqueContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Boolean.
		 *
		 * @return the terminal node
		 */
		public TerminalNode BOOLEAN() { return getToken(HdbtableParser.BOOLEAN, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new column assign unique context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ColumnAssignUniqueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_columnAssignUnique; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterColumnAssignUnique(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitColumnAssignUnique(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitColumnAssignUnique(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Column assign unique.
	 *
	 * @return the column assign unique context
	 * @throws RecognitionException the recognition exception
	 */
	public final ColumnAssignUniqueContext columnAssignUnique() throws RecognitionException {
		ColumnAssignUniqueContext _localctx = new ColumnAssignUniqueContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_columnAssignUnique);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			match(T__19);
			setState(238);
			match(EQ);
			setState(239);
			match(BOOLEAN);
			setState(240);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class ColumnAssignLengthContext.
	 */
	public static class ColumnAssignLengthContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Int.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INT() { return getToken(HdbtableParser.INT, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new column assign length context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ColumnAssignLengthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_columnAssignLength; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterColumnAssignLength(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitColumnAssignLength(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitColumnAssignLength(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Column assign length.
	 *
	 * @return the column assign length context
	 * @throws RecognitionException the recognition exception
	 */
	public final ColumnAssignLengthContext columnAssignLength() throws RecognitionException {
		ColumnAssignLengthContext _localctx = new ColumnAssignLengthContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_columnAssignLength);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			match(T__20);
			setState(243);
			match(EQ);
			setState(244);
			match(INT);
			setState(245);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class ColumnAssignCommentContext.
	 */
	public static class ColumnAssignCommentContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(HdbtableParser.STRING, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new column assign comment context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ColumnAssignCommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_columnAssignComment; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterColumnAssignComment(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitColumnAssignComment(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitColumnAssignComment(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Column assign comment.
	 *
	 * @return the column assign comment context
	 * @throws RecognitionException the recognition exception
	 */
	public final ColumnAssignCommentContext columnAssignComment() throws RecognitionException {
		ColumnAssignCommentContext _localctx = new ColumnAssignCommentContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_columnAssignComment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(T__21);
			setState(248);
			match(EQ);
			setState(249);
			match(STRING);
			setState(250);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class ColumnAssignDefaultValueContext.
	 */
	public static class ColumnAssignDefaultValueContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(HdbtableParser.STRING, 0); }
		
		/**
		 * Int.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INT() { return getToken(HdbtableParser.INT, 0); }
		
		/**
		 * Datetimedefaultvalues.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DATETIMEDEFAULTVALUES() { return getToken(HdbtableParser.DATETIMEDEFAULTVALUES, 0); }
		
		/**
		 * Instantiates a new column assign default value context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ColumnAssignDefaultValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_columnAssignDefaultValue; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterColumnAssignDefaultValue(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitColumnAssignDefaultValue(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitColumnAssignDefaultValue(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Column assign default value.
	 *
	 * @return the column assign default value context
	 * @throws RecognitionException the recognition exception
	 */
	public final ColumnAssignDefaultValueContext columnAssignDefaultValue() throws RecognitionException {
		ColumnAssignDefaultValueContext _localctx = new ColumnAssignDefaultValueContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_columnAssignDefaultValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(T__22);
			setState(253);
			match(EQ);
			setState(254);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << INT) | (1L << DATETIMEDEFAULTVALUES))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(255);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class ColumnAssignPrecisionContext.
	 */
	public static class ColumnAssignPrecisionContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Int.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INT() { return getToken(HdbtableParser.INT, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new column assign precision context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ColumnAssignPrecisionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_columnAssignPrecision; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterColumnAssignPrecision(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitColumnAssignPrecision(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitColumnAssignPrecision(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Column assign precision.
	 *
	 * @return the column assign precision context
	 * @throws RecognitionException the recognition exception
	 */
	public final ColumnAssignPrecisionContext columnAssignPrecision() throws RecognitionException {
		ColumnAssignPrecisionContext _localctx = new ColumnAssignPrecisionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_columnAssignPrecision);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			match(T__23);
			setState(258);
			match(EQ);
			setState(259);
			match(INT);
			setState(260);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class ColumnAssignScaleContext.
	 */
	public static class ColumnAssignScaleContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Int.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INT() { return getToken(HdbtableParser.INT, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new column assign scale context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ColumnAssignScaleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_columnAssignScale; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterColumnAssignScale(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitColumnAssignScale(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitColumnAssignScale(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Column assign scale.
	 *
	 * @return the column assign scale context
	 * @throws RecognitionException the recognition exception
	 */
	public final ColumnAssignScaleContext columnAssignScale() throws RecognitionException {
		ColumnAssignScaleContext _localctx = new ColumnAssignScaleContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_columnAssignScale);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			match(T__24);
			setState(263);
			match(EQ);
			setState(264);
			match(INT);
			setState(265);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class IndexAssignNameContext.
	 */
	public static class IndexAssignNameContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(HdbtableParser.STRING, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new index assign name context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public IndexAssignNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_indexAssignName; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterIndexAssignName(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitIndexAssignName(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitIndexAssignName(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Index assign name.
	 *
	 * @return the index assign name context
	 * @throws RecognitionException the recognition exception
	 */
	public final IndexAssignNameContext indexAssignName() throws RecognitionException {
		IndexAssignNameContext _localctx = new IndexAssignNameContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_indexAssignName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(T__16);
			setState(268);
			match(EQ);
			setState(269);
			match(STRING);
			setState(270);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class IndexAssignUniqueContext.
	 */
	public static class IndexAssignUniqueContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Boolean.
		 *
		 * @return the terminal node
		 */
		public TerminalNode BOOLEAN() { return getToken(HdbtableParser.BOOLEAN, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new index assign unique context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public IndexAssignUniqueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_indexAssignUnique; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterIndexAssignUnique(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitIndexAssignUnique(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitIndexAssignUnique(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Index assign unique.
	 *
	 * @return the index assign unique context
	 * @throws RecognitionException the recognition exception
	 */
	public final IndexAssignUniqueContext indexAssignUnique() throws RecognitionException {
		IndexAssignUniqueContext _localctx = new IndexAssignUniqueContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_indexAssignUnique);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			match(T__19);
			setState(273);
			match(EQ);
			setState(274);
			match(BOOLEAN);
			setState(275);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class IndexAssignOrderContext.
	 */
	public static class IndexAssignOrderContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Order.
		 *
		 * @return the terminal node
		 */
		public TerminalNode ORDER() { return getToken(HdbtableParser.ORDER, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new index assign order context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public IndexAssignOrderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_indexAssignOrder; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterIndexAssignOrder(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitIndexAssignOrder(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitIndexAssignOrder(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Index assign order.
	 *
	 * @return the index assign order context
	 * @throws RecognitionException the recognition exception
	 */
	public final IndexAssignOrderContext indexAssignOrder() throws RecognitionException {
		IndexAssignOrderContext _localctx = new IndexAssignOrderContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_indexAssignOrder);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			match(T__25);
			setState(278);
			match(EQ);
			setState(279);
			match(ORDER);
			setState(280);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class IndexAssignIndexColumnsContext.
	 */
	public static class IndexAssignIndexColumnsContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Index columns array.
		 *
		 * @return the index columns array context
		 */
		public IndexColumnsArrayContext indexColumnsArray() {
			return getRuleContext(IndexColumnsArrayContext.class,0);
		}
		
		/**
		 * Instantiates a new index assign index columns context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public IndexAssignIndexColumnsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_indexAssignIndexColumns; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterIndexAssignIndexColumns(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitIndexAssignIndexColumns(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitIndexAssignIndexColumns(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Index assign index columns.
	 *
	 * @return the index assign index columns context
	 * @throws RecognitionException the recognition exception
	 */
	public final IndexAssignIndexColumnsContext indexAssignIndexColumns() throws RecognitionException {
		IndexAssignIndexColumnsContext _localctx = new IndexAssignIndexColumnsContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_indexAssignIndexColumns);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			match(T__26);
			setState(283);
			match(EQ);
			setState(284);
			indexColumnsArray();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class IndexAssignIndexTypeContext.
	 */
	public static class IndexAssignIndexTypeContext extends ParserRuleContext {
		
		/**
		 * Eq.
		 *
		 * @return the terminal node
		 */
		public TerminalNode EQ() { return getToken(HdbtableParser.EQ, 0); }
		
		/**
		 * Indextype.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INDEXTYPE() { return getToken(HdbtableParser.INDEXTYPE, 0); }
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * Instantiates a new index assign index type context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public IndexAssignIndexTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_indexAssignIndexType; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterIndexAssignIndexType(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitIndexAssignIndexType(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitIndexAssignIndexType(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Index assign index type.
	 *
	 * @return the index assign index type context
	 * @throws RecognitionException the recognition exception
	 */
	public final IndexAssignIndexTypeContext indexAssignIndexType() throws RecognitionException {
		IndexAssignIndexTypeContext _localctx = new IndexAssignIndexTypeContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_indexAssignIndexType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			match(T__12);
			setState(287);
			match(EQ);
			setState(288);
			match(INDEXTYPE);
			setState(289);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/**
	 * The Class IndexColumnsArrayContext.
	 */
	public static class IndexColumnsArrayContext extends ParserRuleContext {
		
		/**
		 * Semicolon.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLON() { return getToken(HdbtableParser.SEMICOLON, 0); }
		
		/**
		 * String.
		 *
		 * @return the list
		 */
		public List<TerminalNode> STRING() { return getTokens(HdbtableParser.STRING); }
		
		/**
		 * String.
		 *
		 * @param i the i
		 * @return the terminal node
		 */
		public TerminalNode STRING(int i) {
			return getToken(HdbtableParser.STRING, i);
		}
		
		/**
		 * Instantiates a new index columns array context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public IndexColumnsArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_indexColumnsArray; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).enterIndexColumnsArray(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof HdbtableListener ) ((HdbtableListener)listener).exitIndexColumnsArray(this);
		}
		
		/**
		 * Accept.
		 *
		 * @param <T> the generic type
		 * @param visitor the visitor
		 * @return the t
		 */
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HdbtableVisitor ) return ((HdbtableVisitor<? extends T>)visitor).visitIndexColumnsArray(this);
			else return visitor.visitChildren(this);
		}
	}

	/**
	 * Index columns array.
	 *
	 * @return the index columns array context
	 * @throws RecognitionException the recognition exception
	 */
	public final IndexColumnsArrayContext indexColumnsArray() throws RecognitionException {
		IndexColumnsArrayContext _localctx = new IndexColumnsArrayContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_indexColumnsArray);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			match(T__6);
			setState(300);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STRING) {
				{
				setState(292);
				match(STRING);
				setState(297);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(293);
					match(T__7);
					setState(294);
					match(STRING);
					}
					}
					setState(299);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(302);
			match(T__8);
			setState(303);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	/** The Constant _serializedATN. */
	public static final String _serializedATN =
		"\u0004\u0001+\u0132\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0001\u0000\u0004\u0000B\b\u0000\u000b\u0000"+
		"\f\u0000C\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001"+
		"P\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0005\u0007}\b\u0007\n\u0007\f\u0007\u0080\t"+
		"\u0007\u0003\u0007\u0082\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b\u008f"+
		"\b\b\n\b\f\b\u0092\t\b\u0003\b\u0094\b\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0005\n\u00a6\b\n\n\n\f\n\u00a9\t\n\u0001\n"+
		"\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0004\r\u00bf\b\r\u000b\r\f"+
		"\r\u00c0\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u00ce\b\u000e\u0001\u000f\u0001\u000f\u0004\u000f\u00d2\b\u000f\u000b"+
		"\u000f\f\u000f\u00d3\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00dd\b\u0010\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0005"+
		"\u001f\u0128\b\u001f\n\u001f\f\u001f\u012b\t\u001f\u0003\u001f\u012d\b"+
		"\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0000\u0000 \u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c"+
		"\u001e \"$&(*,.02468:<>\u0000\u0001\u0003\u0000\u001c\u001c&&))\u0130"+
		"\u0000A\u0001\u0000\u0000\u0000\u0002O\u0001\u0000\u0000\u0000\u0004Q"+
		"\u0001\u0000\u0000\u0000\u0006X\u0001\u0000\u0000\u0000\b_\u0001\u0000"+
		"\u0000\u0000\nf\u0001\u0000\u0000\u0000\fm\u0001\u0000\u0000\u0000\u000e"+
		"t\u0001\u0000\u0000\u0000\u0010\u0086\u0001\u0000\u0000\u0000\u0012\u0098"+
		"\u0001\u0000\u0000\u0000\u0014\u00a1\u0001\u0000\u0000\u0000\u0016\u00ac"+
		"\u0001\u0000\u0000\u0000\u0018\u00b5\u0001\u0000\u0000\u0000\u001a\u00bc"+
		"\u0001\u0000\u0000\u0000\u001c\u00cd\u0001\u0000\u0000\u0000\u001e\u00cf"+
		"\u0001\u0000\u0000\u0000 \u00dc\u0001\u0000\u0000\u0000\"\u00de\u0001"+
		"\u0000\u0000\u0000$\u00e3\u0001\u0000\u0000\u0000&\u00e8\u0001\u0000\u0000"+
		"\u0000(\u00ed\u0001\u0000\u0000\u0000*\u00f2\u0001\u0000\u0000\u0000,"+
		"\u00f7\u0001\u0000\u0000\u0000.\u00fc\u0001\u0000\u0000\u00000\u0101\u0001"+
		"\u0000\u0000\u00002\u0106\u0001\u0000\u0000\u00004\u010b\u0001\u0000\u0000"+
		"\u00006\u0110\u0001\u0000\u0000\u00008\u0115\u0001\u0000\u0000\u0000:"+
		"\u011a\u0001\u0000\u0000\u0000<\u011e\u0001\u0000\u0000\u0000>\u0123\u0001"+
		"\u0000\u0000\u0000@B\u0003\u0002\u0001\u0000A@\u0001\u0000\u0000\u0000"+
		"BC\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000"+
		"\u0000D\u0001\u0001\u0000\u0000\u0000EP\u0003\u0004\u0002\u0000FP\u0003"+
		"\u0006\u0003\u0000GP\u0003\b\u0004\u0000HP\u0003\n\u0005\u0000IP\u0003"+
		"\f\u0006\u0000JP\u0003\u000e\u0007\u0000KP\u0003\u0010\b\u0000LP\u0003"+
		"\u0012\t\u0000MP\u0003\u0016\u000b\u0000NP\u0003\u0018\f\u0000OE\u0001"+
		"\u0000\u0000\u0000OF\u0001\u0000\u0000\u0000OG\u0001\u0000\u0000\u0000"+
		"OH\u0001\u0000\u0000\u0000OI\u0001\u0000\u0000\u0000OJ\u0001\u0000\u0000"+
		"\u0000OK\u0001\u0000\u0000\u0000OL\u0001\u0000\u0000\u0000OM\u0001\u0000"+
		"\u0000\u0000ON\u0001\u0000\u0000\u0000P\u0003\u0001\u0000\u0000\u0000"+
		"QR\u0005\u001e\u0000\u0000RS\u0005\u001f\u0000\u0000ST\u0005\u0001\u0000"+
		"\u0000TU\u0005 \u0000\u0000UV\u0005\u001c\u0000\u0000VW\u0005!\u0000\u0000"+
		"W\u0005\u0001\u0000\u0000\u0000XY\u0005\u001e\u0000\u0000YZ\u0005\u001f"+
		"\u0000\u0000Z[\u0005\u0002\u0000\u0000[\\\u0005 \u0000\u0000\\]\u0005"+
		"#\u0000\u0000]^\u0005!\u0000\u0000^\u0007\u0001\u0000\u0000\u0000_`\u0005"+
		"\u001e\u0000\u0000`a\u0005\u001f\u0000\u0000ab\u0005\u0003\u0000\u0000"+
		"bc\u0005 \u0000\u0000cd\u0005\'\u0000\u0000de\u0005!\u0000\u0000e\t\u0001"+
		"\u0000\u0000\u0000fg\u0005\u001e\u0000\u0000gh\u0005\u001f\u0000\u0000"+
		"hi\u0005\u0004\u0000\u0000ij\u0005 \u0000\u0000jk\u0005#\u0000\u0000k"+
		"l\u0005!\u0000\u0000l\u000b\u0001\u0000\u0000\u0000mn\u0005\u001e\u0000"+
		"\u0000no\u0005\u001f\u0000\u0000op\u0005\u0005\u0000\u0000pq\u0005 \u0000"+
		"\u0000qr\u0005(\u0000\u0000rs\u0005!\u0000\u0000s\r\u0001\u0000\u0000"+
		"\u0000tu\u0005\u001e\u0000\u0000uv\u0005\u001f\u0000\u0000vw\u0005\u0006"+
		"\u0000\u0000wx\u0005 \u0000\u0000x\u0081\u0005\u0007\u0000\u0000y~\u0003"+
		"\u001a\r\u0000z{\u0005\b\u0000\u0000{}\u0003\u001a\r\u0000|z\u0001\u0000"+
		"\u0000\u0000}\u0080\u0001\u0000\u0000\u0000~|\u0001\u0000\u0000\u0000"+
		"~\u007f\u0001\u0000\u0000\u0000\u007f\u0082\u0001\u0000\u0000\u0000\u0080"+
		"~\u0001\u0000\u0000\u0000\u0081y\u0001\u0000\u0000\u0000\u0081\u0082\u0001"+
		"\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0084\u0005"+
		"\t\u0000\u0000\u0084\u0085\u0005!\u0000\u0000\u0085\u000f\u0001\u0000"+
		"\u0000\u0000\u0086\u0087\u0005\u001e\u0000\u0000\u0087\u0088\u0005\u001f"+
		"\u0000\u0000\u0088\u0089\u0005\n\u0000\u0000\u0089\u008a\u0005 \u0000"+
		"\u0000\u008a\u0093\u0005\u0007\u0000\u0000\u008b\u0090\u0003\u001e\u000f"+
		"\u0000\u008c\u008d\u0005\b\u0000\u0000\u008d\u008f\u0003\u001e\u000f\u0000"+
		"\u008e\u008c\u0001\u0000\u0000\u0000\u008f\u0092\u0001\u0000\u0000\u0000"+
		"\u0090\u008e\u0001\u0000\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000"+
		"\u0091\u0094\u0001\u0000\u0000\u0000\u0092\u0090\u0001\u0000\u0000\u0000"+
		"\u0093\u008b\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000"+
		"\u0094\u0095\u0001\u0000\u0000\u0000\u0095\u0096\u0005\t\u0000\u0000\u0096"+
		"\u0097\u0005!\u0000\u0000\u0097\u0011\u0001\u0000\u0000\u0000\u0098\u0099"+
		"\u0005\u001e\u0000\u0000\u0099\u009a\u0005\u001f\u0000\u0000\u009a\u009b"+
		"\u0005\u000b\u0000\u0000\u009b\u009c\u0005\u001f\u0000\u0000\u009c\u009d"+
		"\u0005\f\u0000\u0000\u009d\u009e\u0005 \u0000\u0000\u009e\u009f\u0003"+
		"\u0014\n\u0000\u009f\u00a0\u0005!\u0000\u0000\u00a0\u0013\u0001\u0000"+
		"\u0000\u0000\u00a1\u00a2\u0005\u0007\u0000\u0000\u00a2\u00a7\u0005\u001c"+
		"\u0000\u0000\u00a3\u00a4\u0005\b\u0000\u0000\u00a4\u00a6\u0005\u001c\u0000"+
		"\u0000\u00a5\u00a3\u0001\u0000\u0000\u0000\u00a6\u00a9\u0001\u0000\u0000"+
		"\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000"+
		"\u0000\u00a8\u00aa\u0001\u0000\u0000\u0000\u00a9\u00a7\u0001\u0000\u0000"+
		"\u0000\u00aa\u00ab\u0005\t\u0000\u0000\u00ab\u0015\u0001\u0000\u0000\u0000"+
		"\u00ac\u00ad\u0005\u001e\u0000\u0000\u00ad\u00ae\u0005\u001f\u0000\u0000"+
		"\u00ae\u00af\u0005\u000b\u0000\u0000\u00af\u00b0\u0005\u001f\u0000\u0000"+
		"\u00b0\u00b1\u0005\r\u0000\u0000\u00b1\u00b2\u0005 \u0000\u0000\u00b2"+
		"\u00b3\u0005%\u0000\u0000\u00b3\u00b4\u0005!\u0000\u0000\u00b4\u0017\u0001"+
		"\u0000\u0000\u0000\u00b5\u00b6\u0005\u001e\u0000\u0000\u00b6\u00b7\u0005"+
		"\u001f\u0000\u0000\u00b7\u00b8\u0005\u000e\u0000\u0000\u00b8\u00b9\u0005"+
		" \u0000\u0000\u00b9\u00ba\u0005\u001c\u0000\u0000\u00ba\u00bb\u0005!\u0000"+
		"\u0000\u00bb\u0019\u0001\u0000\u0000\u0000\u00bc\u00be\u0005\u000f\u0000"+
		"\u0000\u00bd\u00bf\u0003\u001c\u000e\u0000\u00be\u00bd\u0001\u0000\u0000"+
		"\u0000\u00bf\u00c0\u0001\u0000\u0000\u0000\u00c0\u00be\u0001\u0000\u0000"+
		"\u0000\u00c0\u00c1\u0001\u0000\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000"+
		"\u0000\u00c2\u00c3\u0005\u0010\u0000\u0000\u00c3\u001b\u0001\u0000\u0000"+
		"\u0000\u00c4\u00ce\u0003\"\u0011\u0000\u00c5\u00ce\u0003$\u0012\u0000"+
		"\u00c6\u00ce\u0003(\u0014\u0000\u00c7\u00ce\u0003*\u0015\u0000\u00c8\u00ce"+
		"\u0003&\u0013\u0000\u00c9\u00ce\u0003,\u0016\u0000\u00ca\u00ce\u0003."+
		"\u0017\u0000\u00cb\u00ce\u00030\u0018\u0000\u00cc\u00ce\u00032\u0019\u0000"+
		"\u00cd\u00c4\u0001\u0000\u0000\u0000\u00cd\u00c5\u0001\u0000\u0000\u0000"+
		"\u00cd\u00c6\u0001\u0000\u0000\u0000\u00cd\u00c7\u0001\u0000\u0000\u0000"+
		"\u00cd\u00c8\u0001\u0000\u0000\u0000\u00cd\u00c9\u0001\u0000\u0000\u0000"+
		"\u00cd\u00ca\u0001\u0000\u0000\u0000\u00cd\u00cb\u0001\u0000\u0000\u0000"+
		"\u00cd\u00cc\u0001\u0000\u0000\u0000\u00ce\u001d\u0001\u0000\u0000\u0000"+
		"\u00cf\u00d1\u0005\u000f\u0000\u0000\u00d0\u00d2\u0003 \u0010\u0000\u00d1"+
		"\u00d0\u0001\u0000\u0000\u0000\u00d2\u00d3\u0001\u0000\u0000\u0000\u00d3"+
		"\u00d1\u0001\u0000\u0000\u0000\u00d3\u00d4\u0001\u0000\u0000\u0000\u00d4"+
		"\u00d5\u0001\u0000\u0000\u0000\u00d5\u00d6\u0005\u0010\u0000\u0000\u00d6"+
		"\u001f\u0001\u0000\u0000\u0000\u00d7\u00dd\u00034\u001a\u0000\u00d8\u00dd"+
		"\u00036\u001b\u0000\u00d9\u00dd\u00038\u001c\u0000\u00da\u00dd\u0003:"+
		"\u001d\u0000\u00db\u00dd\u0003<\u001e\u0000\u00dc\u00d7\u0001\u0000\u0000"+
		"\u0000\u00dc\u00d8\u0001\u0000\u0000\u0000\u00dc\u00d9\u0001\u0000\u0000"+
		"\u0000\u00dc\u00da\u0001\u0000\u0000\u0000\u00dc\u00db\u0001\u0000\u0000"+
		"\u0000\u00dd!\u0001\u0000\u0000\u0000\u00de\u00df\u0005\u0011\u0000\u0000"+
		"\u00df\u00e0\u0005 \u0000\u0000\u00e0\u00e1\u0005\u001c\u0000\u0000\u00e1"+
		"\u00e2\u0005!\u0000\u0000\u00e2#\u0001\u0000\u0000\u0000\u00e3\u00e4\u0005"+
		"\u0012\u0000\u0000\u00e4\u00e5\u0005 \u0000\u0000\u00e5\u00e6\u0005\""+
		"\u0000\u0000\u00e6\u00e7\u0005!\u0000\u0000\u00e7%\u0001\u0000\u0000\u0000"+
		"\u00e8\u00e9\u0005\u0013\u0000\u0000\u00e9\u00ea\u0005 \u0000\u0000\u00ea"+
		"\u00eb\u0005#\u0000\u0000\u00eb\u00ec\u0005!\u0000\u0000\u00ec\'\u0001"+
		"\u0000\u0000\u0000\u00ed\u00ee\u0005\u0014\u0000\u0000\u00ee\u00ef\u0005"+
		" \u0000\u0000\u00ef\u00f0\u0005#\u0000\u0000\u00f0\u00f1\u0005!\u0000"+
		"\u0000\u00f1)\u0001\u0000\u0000\u0000\u00f2\u00f3\u0005\u0015\u0000\u0000"+
		"\u00f3\u00f4\u0005 \u0000\u0000\u00f4\u00f5\u0005&\u0000\u0000\u00f5\u00f6"+
		"\u0005!\u0000\u0000\u00f6+\u0001\u0000\u0000\u0000\u00f7\u00f8\u0005\u0016"+
		"\u0000\u0000\u00f8\u00f9\u0005 \u0000\u0000\u00f9\u00fa\u0005\u001c\u0000"+
		"\u0000\u00fa\u00fb\u0005!\u0000\u0000\u00fb-\u0001\u0000\u0000\u0000\u00fc"+
		"\u00fd\u0005\u0017\u0000\u0000\u00fd\u00fe\u0005 \u0000\u0000\u00fe\u00ff"+
		"\u0007\u0000\u0000\u0000\u00ff\u0100\u0005!\u0000\u0000\u0100/\u0001\u0000"+
		"\u0000\u0000\u0101\u0102\u0005\u0018\u0000\u0000\u0102\u0103\u0005 \u0000"+
		"\u0000\u0103\u0104\u0005&\u0000\u0000\u0104\u0105\u0005!\u0000\u0000\u0105"+
		"1\u0001\u0000\u0000\u0000\u0106\u0107\u0005\u0019\u0000\u0000\u0107\u0108"+
		"\u0005 \u0000\u0000\u0108\u0109\u0005&\u0000\u0000\u0109\u010a\u0005!"+
		"\u0000\u0000\u010a3\u0001\u0000\u0000\u0000\u010b\u010c\u0005\u0011\u0000"+
		"\u0000\u010c\u010d\u0005 \u0000\u0000\u010d\u010e\u0005\u001c\u0000\u0000"+
		"\u010e\u010f\u0005!\u0000\u0000\u010f5\u0001\u0000\u0000\u0000\u0110\u0111"+
		"\u0005\u0014\u0000\u0000\u0111\u0112\u0005 \u0000\u0000\u0112\u0113\u0005"+
		"#\u0000\u0000\u0113\u0114\u0005!\u0000\u0000\u01147\u0001\u0000\u0000"+
		"\u0000\u0115\u0116\u0005\u001a\u0000\u0000\u0116\u0117\u0005 \u0000\u0000"+
		"\u0117\u0118\u0005$\u0000\u0000\u0118\u0119\u0005!\u0000\u0000\u01199"+
		"\u0001\u0000\u0000\u0000\u011a\u011b\u0005\u001b\u0000\u0000\u011b\u011c"+
		"\u0005 \u0000\u0000\u011c\u011d\u0003>\u001f\u0000\u011d;\u0001\u0000"+
		"\u0000\u0000\u011e\u011f\u0005\r\u0000\u0000\u011f\u0120\u0005 \u0000"+
		"\u0000\u0120\u0121\u0005%\u0000\u0000\u0121\u0122\u0005!\u0000\u0000\u0122"+
		"=\u0001\u0000\u0000\u0000\u0123\u012c\u0005\u0007\u0000\u0000\u0124\u0129"+
		"\u0005\u001c\u0000\u0000\u0125\u0126\u0005\b\u0000\u0000\u0126\u0128\u0005"+
		"\u001c\u0000\u0000\u0127\u0125\u0001\u0000\u0000\u0000\u0128\u012b\u0001"+
		"\u0000\u0000\u0000\u0129\u0127\u0001\u0000\u0000\u0000\u0129\u012a\u0001"+
		"\u0000\u0000\u0000\u012a\u012d\u0001\u0000\u0000\u0000\u012b\u0129\u0001"+
		"\u0000\u0000\u0000\u012c\u0124\u0001\u0000\u0000\u0000\u012c\u012d\u0001"+
		"\u0000\u0000\u0000\u012d\u012e\u0001\u0000\u0000\u0000\u012e\u012f\u0005"+
		"\t\u0000\u0000\u012f\u0130\u0005!\u0000\u0000\u0130?\u0001\u0000\u0000"+
		"\u0000\rCO~\u0081\u0090\u0093\u00a7\u00c0\u00cd\u00d3\u00dc\u0129\u012c";
	
	/** The Constant _ATN. */
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
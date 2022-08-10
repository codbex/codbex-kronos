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
// Generated from com/codbex/kronos/parser/hdbdd/core/Cds.g4 by ANTLR 4.10.1
package com.codbex.kronos.parser.hdbdd.core;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * The Class CdsParser.
 */
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CdsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	/** The Constant _decisionToDFA. */
	protected static final DFA[] _decisionToDFA;
	
	/** The Constant _sharedContextCache. */
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	
	/** The Constant Z. */
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, NAMESPACE=19, AS=20, ON=21, SELECT=22, FROM=23, WHERE=24, DEFINE=25, 
		UNION=26, DISTINCT=27, HANA=28, JOIN_TYPES=29, INNER_JOIN=30, LEFT_JOIN=31, 
		LEFT_OUTER_JOIN=32, RIGHT_OUTER_JOIN=33, FULL_OUTER_JOIN=34, REFERNTIAL_JOIN=35, 
		TEXT_JOIN=36, DATETIME_VALUE_FUNCTION=37, USING=38, NULL=39, CONCATENATION=40, 
		NOT_EQUAL_TO=41, DEFAULT=42, ASSOCIATION_MIN=43, BOOLEAN=44, CONTEXT=45, 
		ENTITY=46, VIEW=47, TYPE=48, ASSOCIATION=49, TO=50, ID=51, SEMICOLUMN=52, 
		INTEGER=53, DECIMAL=54, LOCAL_TIME=55, LOCAL_DATE=56, UTC_DATE_TIME=57, 
		UTC_TIMESTAMP=58, STRING=59, VARBINARY=60, TYPE_OF=61, WS=62, LINE_COMMENT1=63, 
		LINE_COMMENT2=64, A=65, B=66, C=67, D=68, E=69, F=70, G=71, H=72, I=73, 
		J=74, K=75, L=76, M=77, N=78, O=79, P=80, Q=81, R=82, S=83, T=84, U=85, 
		V=86, W=87, X=88, Y=89, Z=90;
	
	/** The Constant RULE_identifier. */
	public static final int
		RULE_cdsFile = 0, RULE_namespaceRule = 1, RULE_usingRule = 2, RULE_topLevelSymbol = 3, 
		RULE_dataTypeRule = 4, RULE_contextRule = 5, RULE_structuredTypeRule = 6, 
		RULE_entityRule = 7, RULE_viewRule = 8, RULE_fieldDeclRule = 9, RULE_typeAssignRule = 10, 
		RULE_elementDeclRule = 11, RULE_elementDetails = 12, RULE_elementConstraints = 13, 
		RULE_associationConstraints = 14, RULE_constraints = 15, RULE_association = 16, 
		RULE_calculatedAssociation = 17, RULE_statement = 18, RULE_associationTarget = 19, 
		RULE_unmanagedForeignKey = 20, RULE_managedForeignKeys = 21, RULE_foreignKey = 22, 
		RULE_cardinality = 23, RULE_defaultValue = 24, RULE_annotationRule = 25, 
		RULE_annValue = 26, RULE_enumRule = 27, RULE_arrRule = 28, RULE_obj = 29, 
		RULE_keyValue = 30, RULE_selectRule = 31, RULE_joinRule = 32, RULE_joinFields = 33, 
		RULE_selectedColumnsRule = 34, RULE_whereRule = 35, RULE_identifier = 36;
	
	/**
	 * Make rule names.
	 *
	 * @return the string[]
	 */
	private static String[] makeRuleNames() {
		return new String[] {
			"cdsFile", "namespaceRule", "usingRule", "topLevelSymbol", "dataTypeRule", 
			"contextRule", "structuredTypeRule", "entityRule", "viewRule", "fieldDeclRule", 
			"typeAssignRule", "elementDeclRule", "elementDetails", "elementConstraints", 
			"associationConstraints", "constraints", "association", "calculatedAssociation", 
			"statement", "associationTarget", "unmanagedForeignKey", "managedForeignKeys", 
			"foreignKey", "cardinality", "defaultValue", "annotationRule", "annValue", 
			"enumRule", "arrRule", "obj", "keyValue", "selectRule", "joinRule", "joinFields", 
			"selectedColumnsRule", "whereRule", "identifier"
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
			null, "'.'", "'::'", "':'", "'{'", "'}'", "'\"'", "'('", "','", "')'", 
			"'not null'", "'NULL'", "'NOT NULL'", "'='", "'['", "'*'", "']'", "'@'", 
			"'#'", null, null, null, null, null, null, null, null, null, "'hana'", 
			null, null, null, null, null, null, null, null, null, null, "'null'", 
			"'||'", "'<>'", null, null, null, null, null, null, null, null, null, 
			null, "';'"
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
			null, null, null, null, null, null, null, "NAMESPACE", "AS", "ON", "SELECT", 
			"FROM", "WHERE", "DEFINE", "UNION", "DISTINCT", "HANA", "JOIN_TYPES", 
			"INNER_JOIN", "LEFT_JOIN", "LEFT_OUTER_JOIN", "RIGHT_OUTER_JOIN", "FULL_OUTER_JOIN", 
			"REFERNTIAL_JOIN", "TEXT_JOIN", "DATETIME_VALUE_FUNCTION", "USING", "NULL", 
			"CONCATENATION", "NOT_EQUAL_TO", "DEFAULT", "ASSOCIATION_MIN", "BOOLEAN", 
			"CONTEXT", "ENTITY", "VIEW", "TYPE", "ASSOCIATION", "TO", "ID", "SEMICOLUMN", 
			"INTEGER", "DECIMAL", "LOCAL_TIME", "LOCAL_DATE", "UTC_DATE_TIME", "UTC_TIMESTAMP", 
			"STRING", "VARBINARY", "TYPE_OF", "WS", "LINE_COMMENT1", "LINE_COMMENT2", 
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", 
			"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
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
	public String getGrammarFileName() { return "Cds.g4"; }

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
	 * Instantiates a new cds parser.
	 *
	 * @param input the input
	 */
	public CdsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	/**
	 * The Class CdsFileContext.
	 */
	public static class CdsFileContext extends ParserRuleContext {
		
		/**
		 * Namespace rule.
		 *
		 * @return the namespace rule context
		 */
		public NamespaceRuleContext namespaceRule() {
			return getRuleContext(NamespaceRuleContext.class,0);
		}
		
		/**
		 * Top level symbol.
		 *
		 * @return the top level symbol context
		 */
		public TopLevelSymbolContext topLevelSymbol() {
			return getRuleContext(TopLevelSymbolContext.class,0);
		}
		
		/**
		 * Using rule.
		 *
		 * @return the list
		 */
		public List<UsingRuleContext> usingRule() {
			return getRuleContexts(UsingRuleContext.class);
		}
		
		/**
		 * Using rule.
		 *
		 * @param i the i
		 * @return the using rule context
		 */
		public UsingRuleContext usingRule(int i) {
			return getRuleContext(UsingRuleContext.class,i);
		}
		
		/**
		 * Instantiates a new cds file context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public CdsFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_cdsFile; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterCdsFile(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitCdsFile(this);
		}
	}

	/**
	 * Cds file.
	 *
	 * @return the cds file context
	 * @throws RecognitionException the recognition exception
	 */
	public final CdsFileContext cdsFile() throws RecognitionException {
		CdsFileContext _localctx = new CdsFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_cdsFile);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			namespaceRule();
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==USING) {
				{
				{
				setState(75);
				usingRule();
				}
				}
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(81);
			topLevelSymbol();
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
	 * The Class NamespaceRuleContext.
	 */
	public static class NamespaceRuleContext extends ParserRuleContext {
		
		/** The identifier. */
		public IdentifierContext identifier;
		
		/** The members. */
		public List<IdentifierContext> members = new ArrayList<IdentifierContext>();
		
		/**
		 * Namespace.
		 *
		 * @return the terminal node
		 */
		public TerminalNode NAMESPACE() { return getToken(CdsParser.NAMESPACE, 0); }
		
		/**
		 * Semicolumn.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLUMN() { return getToken(CdsParser.SEMICOLUMN, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the list
		 */
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		
		/**
		 * Identifier.
		 *
		 * @param i the i
		 * @return the identifier context
		 */
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		
		/**
		 * Instantiates a new namespace rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public NamespaceRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_namespaceRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterNamespaceRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitNamespaceRule(this);
		}
	}

	/**
	 * Namespace rule.
	 *
	 * @return the namespace rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final NamespaceRuleContext namespaceRule() throws RecognitionException {
		NamespaceRuleContext _localctx = new NamespaceRuleContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_namespaceRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			match(NAMESPACE);
			setState(84);
			((NamespaceRuleContext)_localctx).identifier = identifier();
			((NamespaceRuleContext)_localctx).members.add(((NamespaceRuleContext)_localctx).identifier);
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(85);
				match(T__0);
				setState(86);
				((NamespaceRuleContext)_localctx).identifier = identifier();
				((NamespaceRuleContext)_localctx).members.add(((NamespaceRuleContext)_localctx).identifier);
				}
				}
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(92);
			match(SEMICOLUMN);
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
	 * The Class UsingRuleContext.
	 */
	public static class UsingRuleContext extends ParserRuleContext {
		
		/** The identifier. */
		public IdentifierContext identifier;
		
		/** The pack. */
		public List<IdentifierContext> pack = new ArrayList<IdentifierContext>();
		
		/** The members. */
		public List<IdentifierContext> members = new ArrayList<IdentifierContext>();
		
		/** The alias. */
		public IdentifierContext alias;
		
		/**
		 * Using.
		 *
		 * @return the terminal node
		 */
		public TerminalNode USING() { return getToken(CdsParser.USING, 0); }
		
		/**
		 * Semicolumn.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLUMN() { return getToken(CdsParser.SEMICOLUMN, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the list
		 */
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		
		/**
		 * Identifier.
		 *
		 * @param i the i
		 * @return the identifier context
		 */
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		
		/**
		 * As.
		 *
		 * @return the terminal node
		 */
		public TerminalNode AS() { return getToken(CdsParser.AS, 0); }
		
		/**
		 * Instantiates a new using rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public UsingRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_usingRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterUsingRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitUsingRule(this);
		}
	}

	/**
	 * Using rule.
	 *
	 * @return the using rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final UsingRuleContext usingRule() throws RecognitionException {
		UsingRuleContext _localctx = new UsingRuleContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_usingRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(USING);
			setState(95);
			((UsingRuleContext)_localctx).identifier = identifier();
			((UsingRuleContext)_localctx).pack.add(((UsingRuleContext)_localctx).identifier);
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(96);
				match(T__0);
				setState(97);
				((UsingRuleContext)_localctx).identifier = identifier();
				((UsingRuleContext)_localctx).pack.add(((UsingRuleContext)_localctx).identifier);
				}
				}
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(103);
			match(T__1);
			setState(104);
			((UsingRuleContext)_localctx).identifier = identifier();
			((UsingRuleContext)_localctx).members.add(((UsingRuleContext)_localctx).identifier);
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(105);
				match(T__0);
				setState(106);
				((UsingRuleContext)_localctx).identifier = identifier();
				((UsingRuleContext)_localctx).members.add(((UsingRuleContext)_localctx).identifier);
				}
				}
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(112);
				match(AS);
				setState(113);
				((UsingRuleContext)_localctx).alias = identifier();
				}
			}

			setState(116);
			match(SEMICOLUMN);
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
	 * The Class TopLevelSymbolContext.
	 */
	public static class TopLevelSymbolContext extends ParserRuleContext {
		
		/**
		 * Data type rule.
		 *
		 * @return the list
		 */
		public List<DataTypeRuleContext> dataTypeRule() {
			return getRuleContexts(DataTypeRuleContext.class);
		}
		
		/**
		 * Data type rule.
		 *
		 * @param i the i
		 * @return the data type rule context
		 */
		public DataTypeRuleContext dataTypeRule(int i) {
			return getRuleContext(DataTypeRuleContext.class,i);
		}
		
		/**
		 * Context rule.
		 *
		 * @return the context rule context
		 */
		public ContextRuleContext contextRule() {
			return getRuleContext(ContextRuleContext.class,0);
		}
		
		/**
		 * Structured type rule.
		 *
		 * @return the structured type rule context
		 */
		public StructuredTypeRuleContext structuredTypeRule() {
			return getRuleContext(StructuredTypeRuleContext.class,0);
		}
		
		/**
		 * Entity rule.
		 *
		 * @return the entity rule context
		 */
		public EntityRuleContext entityRule() {
			return getRuleContext(EntityRuleContext.class,0);
		}
		
		/**
		 * View rule.
		 *
		 * @return the view rule context
		 */
		public ViewRuleContext viewRule() {
			return getRuleContext(ViewRuleContext.class,0);
		}
		
		/**
		 * Instantiates a new top level symbol context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public TopLevelSymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_topLevelSymbol; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterTopLevelSymbol(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitTopLevelSymbol(this);
		}
	}

	/**
	 * Top level symbol.
	 *
	 * @return the top level symbol context
	 * @throws RecognitionException the recognition exception
	 */
	public final TopLevelSymbolContext topLevelSymbol() throws RecognitionException {
		TopLevelSymbolContext _localctx = new TopLevelSymbolContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_topLevelSymbol);
		int _la;
		try {
			setState(136);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__16 || _la==TYPE) {
					{
					{
					setState(118);
					dataTypeRule();
					}
					}
					setState(123);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__16 || _la==CONTEXT) {
					{
					setState(124);
					contextRule();
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(128);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__16 || _la==TYPE) {
					{
					setState(127);
					structuredTypeRule();
					}
				}

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(131);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__16 || _la==ENTITY) {
					{
					setState(130);
					entityRule();
					}
				}

				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(134);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << DEFINE) | (1L << VIEW))) != 0)) {
					{
					setState(133);
					viewRule();
					}
				}

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
	 * The Class DataTypeRuleContext.
	 */
	public static class DataTypeRuleContext extends ParserRuleContext {
		
		/** The artifact type. */
		public Token artifactType;
		
		/** The artifact name. */
		public IdentifierContext artifactName;
		
		/**
		 * Type assign rule.
		 *
		 * @return the type assign rule context
		 */
		public TypeAssignRuleContext typeAssignRule() {
			return getRuleContext(TypeAssignRuleContext.class,0);
		}
		
		/**
		 * Semicolumn.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLUMN() { return getToken(CdsParser.SEMICOLUMN, 0); }
		
		/**
		 * Type.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TYPE() { return getToken(CdsParser.TYPE, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Annotation rule.
		 *
		 * @return the list
		 */
		public List<AnnotationRuleContext> annotationRule() {
			return getRuleContexts(AnnotationRuleContext.class);
		}
		
		/**
		 * Annotation rule.
		 *
		 * @param i the i
		 * @return the annotation rule context
		 */
		public AnnotationRuleContext annotationRule(int i) {
			return getRuleContext(AnnotationRuleContext.class,i);
		}
		
		/**
		 * Instantiates a new data type rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public DataTypeRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_dataTypeRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterDataTypeRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitDataTypeRule(this);
		}
	}

	/**
	 * Data type rule.
	 *
	 * @return the data type rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final DataTypeRuleContext dataTypeRule() throws RecognitionException {
		DataTypeRuleContext _localctx = new DataTypeRuleContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_dataTypeRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(138);
				annotationRule();
				}
				}
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(144);
			((DataTypeRuleContext)_localctx).artifactType = match(TYPE);
			setState(145);
			((DataTypeRuleContext)_localctx).artifactName = identifier();
			setState(146);
			match(T__2);
			setState(147);
			typeAssignRule();
			setState(148);
			match(SEMICOLUMN);
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
	 * The Class ContextRuleContext.
	 */
	public static class ContextRuleContext extends ParserRuleContext {
		
		/** The artifact type. */
		public Token artifactType;
		
		/** The artifact name. */
		public IdentifierContext artifactName;
		
		/**
		 * Context.
		 *
		 * @return the terminal node
		 */
		public TerminalNode CONTEXT() { return getToken(CdsParser.CONTEXT, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Annotation rule.
		 *
		 * @return the list
		 */
		public List<AnnotationRuleContext> annotationRule() {
			return getRuleContexts(AnnotationRuleContext.class);
		}
		
		/**
		 * Annotation rule.
		 *
		 * @param i the i
		 * @return the annotation rule context
		 */
		public AnnotationRuleContext annotationRule(int i) {
			return getRuleContext(AnnotationRuleContext.class,i);
		}
		
		/**
		 * Context rule.
		 *
		 * @return the list
		 */
		public List<ContextRuleContext> contextRule() {
			return getRuleContexts(ContextRuleContext.class);
		}
		
		/**
		 * Context rule.
		 *
		 * @param i the i
		 * @return the context rule context
		 */
		public ContextRuleContext contextRule(int i) {
			return getRuleContext(ContextRuleContext.class,i);
		}
		
		/**
		 * Data type rule.
		 *
		 * @return the list
		 */
		public List<DataTypeRuleContext> dataTypeRule() {
			return getRuleContexts(DataTypeRuleContext.class);
		}
		
		/**
		 * Data type rule.
		 *
		 * @param i the i
		 * @return the data type rule context
		 */
		public DataTypeRuleContext dataTypeRule(int i) {
			return getRuleContext(DataTypeRuleContext.class,i);
		}
		
		/**
		 * Structured type rule.
		 *
		 * @return the list
		 */
		public List<StructuredTypeRuleContext> structuredTypeRule() {
			return getRuleContexts(StructuredTypeRuleContext.class);
		}
		
		/**
		 * Structured type rule.
		 *
		 * @param i the i
		 * @return the structured type rule context
		 */
		public StructuredTypeRuleContext structuredTypeRule(int i) {
			return getRuleContext(StructuredTypeRuleContext.class,i);
		}
		
		/**
		 * Entity rule.
		 *
		 * @return the list
		 */
		public List<EntityRuleContext> entityRule() {
			return getRuleContexts(EntityRuleContext.class);
		}
		
		/**
		 * Entity rule.
		 *
		 * @param i the i
		 * @return the entity rule context
		 */
		public EntityRuleContext entityRule(int i) {
			return getRuleContext(EntityRuleContext.class,i);
		}
		
		/**
		 * View rule.
		 *
		 * @return the list
		 */
		public List<ViewRuleContext> viewRule() {
			return getRuleContexts(ViewRuleContext.class);
		}
		
		/**
		 * View rule.
		 *
		 * @param i the i
		 * @return the view rule context
		 */
		public ViewRuleContext viewRule(int i) {
			return getRuleContext(ViewRuleContext.class,i);
		}
		
		/**
		 * Semicolumn.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLUMN() { return getToken(CdsParser.SEMICOLUMN, 0); }
		
		/**
		 * Instantiates a new context rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ContextRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_contextRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterContextRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitContextRule(this);
		}
	}

	/**
	 * Context rule.
	 *
	 * @return the context rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final ContextRuleContext contextRule() throws RecognitionException {
		ContextRuleContext _localctx = new ContextRuleContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_contextRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(150);
				annotationRule();
				}
				}
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(156);
			((ContextRuleContext)_localctx).artifactType = match(CONTEXT);
			setState(157);
			((ContextRuleContext)_localctx).artifactName = identifier();
			setState(158);
			match(T__3);
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << DEFINE) | (1L << CONTEXT) | (1L << ENTITY) | (1L << VIEW) | (1L << TYPE))) != 0)) {
				{
				setState(164);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
				case 1:
					{
					setState(159);
					contextRule();
					}
					break;
				case 2:
					{
					setState(160);
					dataTypeRule();
					}
					break;
				case 3:
					{
					setState(161);
					structuredTypeRule();
					}
					break;
				case 4:
					{
					setState(162);
					entityRule();
					}
					break;
				case 5:
					{
					setState(163);
					viewRule();
					}
					break;
				}
				}
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(169);
			match(T__4);
			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLUMN) {
				{
				setState(170);
				match(SEMICOLUMN);
				}
			}

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
	 * The Class StructuredTypeRuleContext.
	 */
	public static class StructuredTypeRuleContext extends ParserRuleContext {
		
		/** The artifact type. */
		public Token artifactType;
		
		/** The artifact name. */
		public IdentifierContext artifactName;
		
		/**
		 * Type.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TYPE() { return getToken(CdsParser.TYPE, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Annotation rule.
		 *
		 * @return the list
		 */
		public List<AnnotationRuleContext> annotationRule() {
			return getRuleContexts(AnnotationRuleContext.class);
		}
		
		/**
		 * Annotation rule.
		 *
		 * @param i the i
		 * @return the annotation rule context
		 */
		public AnnotationRuleContext annotationRule(int i) {
			return getRuleContext(AnnotationRuleContext.class,i);
		}
		
		/**
		 * Field decl rule.
		 *
		 * @return the list
		 */
		public List<FieldDeclRuleContext> fieldDeclRule() {
			return getRuleContexts(FieldDeclRuleContext.class);
		}
		
		/**
		 * Field decl rule.
		 *
		 * @param i the i
		 * @return the field decl rule context
		 */
		public FieldDeclRuleContext fieldDeclRule(int i) {
			return getRuleContext(FieldDeclRuleContext.class,i);
		}
		
		/**
		 * Association.
		 *
		 * @return the list
		 */
		public List<AssociationContext> association() {
			return getRuleContexts(AssociationContext.class);
		}
		
		/**
		 * Association.
		 *
		 * @param i the i
		 * @return the association context
		 */
		public AssociationContext association(int i) {
			return getRuleContext(AssociationContext.class,i);
		}
		
		/**
		 * Semicolumn.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLUMN() { return getToken(CdsParser.SEMICOLUMN, 0); }
		
		/**
		 * Instantiates a new structured type rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public StructuredTypeRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_structuredTypeRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterStructuredTypeRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitStructuredTypeRule(this);
		}
	}

	/**
	 * Structured type rule.
	 *
	 * @return the structured type rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final StructuredTypeRuleContext structuredTypeRule() throws RecognitionException {
		StructuredTypeRuleContext _localctx = new StructuredTypeRuleContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_structuredTypeRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(173);
				annotationRule();
				}
				}
				setState(178);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(179);
			((StructuredTypeRuleContext)_localctx).artifactType = match(TYPE);
			setState(180);
			((StructuredTypeRuleContext)_localctx).artifactName = identifier();
			setState(181);
			match(T__3);
			setState(186);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << NAMESPACE) | (1L << AS) | (1L << ON) | (1L << SELECT) | (1L << FROM) | (1L << WHERE) | (1L << DEFINE) | (1L << UNION) | (1L << DISTINCT) | (1L << HANA) | (1L << JOIN_TYPES) | (1L << USING) | (1L << DEFAULT) | (1L << CONTEXT) | (1L << ENTITY) | (1L << VIEW) | (1L << TYPE) | (1L << ASSOCIATION) | (1L << TO) | (1L << ID) | (1L << STRING))) != 0)) {
				{
				setState(184);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
				case 1:
					{
					setState(182);
					fieldDeclRule();
					}
					break;
				case 2:
					{
					setState(183);
					association();
					}
					break;
				}
				}
				setState(188);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(189);
			match(T__4);
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLUMN) {
				{
				setState(190);
				match(SEMICOLUMN);
				}
			}

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
	 * The Class EntityRuleContext.
	 */
	public static class EntityRuleContext extends ParserRuleContext {
		
		/** The artifact type. */
		public Token artifactType;
		
		/** The artifact name. */
		public IdentifierContext artifactName;
		
		/**
		 * Entity.
		 *
		 * @return the terminal node
		 */
		public TerminalNode ENTITY() { return getToken(CdsParser.ENTITY, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Annotation rule.
		 *
		 * @return the list
		 */
		public List<AnnotationRuleContext> annotationRule() {
			return getRuleContexts(AnnotationRuleContext.class);
		}
		
		/**
		 * Annotation rule.
		 *
		 * @param i the i
		 * @return the annotation rule context
		 */
		public AnnotationRuleContext annotationRule(int i) {
			return getRuleContext(AnnotationRuleContext.class,i);
		}
		
		/**
		 * Element decl rule.
		 *
		 * @return the list
		 */
		public List<ElementDeclRuleContext> elementDeclRule() {
			return getRuleContexts(ElementDeclRuleContext.class);
		}
		
		/**
		 * Element decl rule.
		 *
		 * @param i the i
		 * @return the element decl rule context
		 */
		public ElementDeclRuleContext elementDeclRule(int i) {
			return getRuleContext(ElementDeclRuleContext.class,i);
		}
		
		/**
		 * Association.
		 *
		 * @return the list
		 */
		public List<AssociationContext> association() {
			return getRuleContexts(AssociationContext.class);
		}
		
		/**
		 * Association.
		 *
		 * @param i the i
		 * @return the association context
		 */
		public AssociationContext association(int i) {
			return getRuleContext(AssociationContext.class,i);
		}
		
		/**
		 * Calculated association.
		 *
		 * @return the list
		 */
		public List<CalculatedAssociationContext> calculatedAssociation() {
			return getRuleContexts(CalculatedAssociationContext.class);
		}
		
		/**
		 * Calculated association.
		 *
		 * @param i the i
		 * @return the calculated association context
		 */
		public CalculatedAssociationContext calculatedAssociation(int i) {
			return getRuleContext(CalculatedAssociationContext.class,i);
		}
		
		/**
		 * Semicolumn.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLUMN() { return getToken(CdsParser.SEMICOLUMN, 0); }
		
		/**
		 * Instantiates a new entity rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public EntityRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_entityRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterEntityRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitEntityRule(this);
		}
	}

	/**
	 * Entity rule.
	 *
	 * @return the entity rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final EntityRuleContext entityRule() throws RecognitionException {
		EntityRuleContext _localctx = new EntityRuleContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_entityRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(193);
				annotationRule();
				}
				}
				setState(198);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(199);
			((EntityRuleContext)_localctx).artifactType = match(ENTITY);
			setState(200);
			((EntityRuleContext)_localctx).artifactName = identifier();
			setState(201);
			match(T__3);
			setState(207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__16) | (1L << NAMESPACE) | (1L << AS) | (1L << ON) | (1L << SELECT) | (1L << FROM) | (1L << WHERE) | (1L << DEFINE) | (1L << UNION) | (1L << DISTINCT) | (1L << HANA) | (1L << JOIN_TYPES) | (1L << USING) | (1L << DEFAULT) | (1L << CONTEXT) | (1L << ENTITY) | (1L << VIEW) | (1L << TYPE) | (1L << ASSOCIATION) | (1L << TO) | (1L << ID) | (1L << STRING))) != 0)) {
				{
				setState(205);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					{
					setState(202);
					elementDeclRule();
					}
					break;
				case 2:
					{
					setState(203);
					association();
					}
					break;
				case 3:
					{
					setState(204);
					calculatedAssociation();
					}
					break;
				}
				}
				setState(209);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(210);
			match(T__4);
			setState(212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLUMN) {
				{
				setState(211);
				match(SEMICOLUMN);
				}
			}

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
	 * The Class ViewRuleContext.
	 */
	public static class ViewRuleContext extends ParserRuleContext {
		
		/** The artifact type. */
		public Token artifactType;
		
		/** The artifact name. */
		public IdentifierContext artifactName;
		
		/**
		 * As.
		 *
		 * @return the terminal node
		 */
		public TerminalNode AS() { return getToken(CdsParser.AS, 0); }
		
		/**
		 * View.
		 *
		 * @return the terminal node
		 */
		public TerminalNode VIEW() { return getToken(CdsParser.VIEW, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Annotation rule.
		 *
		 * @return the list
		 */
		public List<AnnotationRuleContext> annotationRule() {
			return getRuleContexts(AnnotationRuleContext.class);
		}
		
		/**
		 * Annotation rule.
		 *
		 * @param i the i
		 * @return the annotation rule context
		 */
		public AnnotationRuleContext annotationRule(int i) {
			return getRuleContext(AnnotationRuleContext.class,i);
		}
		
		/**
		 * Define.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DEFINE() { return getToken(CdsParser.DEFINE, 0); }
		
		/**
		 * Select rule.
		 *
		 * @return the list
		 */
		public List<SelectRuleContext> selectRule() {
			return getRuleContexts(SelectRuleContext.class);
		}
		
		/**
		 * Select rule.
		 *
		 * @param i the i
		 * @return the select rule context
		 */
		public SelectRuleContext selectRule(int i) {
			return getRuleContext(SelectRuleContext.class,i);
		}
		
		/**
		 * Instantiates a new view rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ViewRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_viewRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterViewRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitViewRule(this);
		}
	}

	/**
	 * View rule.
	 *
	 * @return the view rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final ViewRuleContext viewRule() throws RecognitionException {
		ViewRuleContext _localctx = new ViewRuleContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_viewRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(214);
				annotationRule();
				}
				}
				setState(219);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(221);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFINE) {
				{
				setState(220);
				match(DEFINE);
				}
			}

			setState(223);
			((ViewRuleContext)_localctx).artifactType = match(VIEW);
			setState(224);
			((ViewRuleContext)_localctx).artifactName = identifier();
			setState(225);
			match(AS);
			setState(229);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SELECT || _la==UNION) {
				{
				{
				setState(226);
				selectRule();
				}
				}
				setState(231);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	 * The Class FieldDeclRuleContext.
	 */
	public static class FieldDeclRuleContext extends ParserRuleContext {
		
		/**
		 * Type assign rule.
		 *
		 * @return the type assign rule context
		 */
		public TypeAssignRuleContext typeAssignRule() {
			return getRuleContext(TypeAssignRuleContext.class,0);
		}
		
		/**
		 * Semicolumn.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLUMN() { return getToken(CdsParser.SEMICOLUMN, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Instantiates a new field decl rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public FieldDeclRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_fieldDeclRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterFieldDeclRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitFieldDeclRule(this);
		}
	}

	/**
	 * Field decl rule.
	 *
	 * @return the field decl rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final FieldDeclRuleContext fieldDeclRule() throws RecognitionException {
		FieldDeclRuleContext _localctx = new FieldDeclRuleContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_fieldDeclRule);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAMESPACE:
			case AS:
			case ON:
			case SELECT:
			case FROM:
			case WHERE:
			case DEFINE:
			case UNION:
			case DISTINCT:
			case HANA:
			case JOIN_TYPES:
			case USING:
			case DEFAULT:
			case CONTEXT:
			case ENTITY:
			case VIEW:
			case TYPE:
			case ASSOCIATION:
			case TO:
			case ID:
			case STRING:
				{
				setState(232);
				identifier();
				}
				break;
			case T__5:
				{
				setState(233);
				match(T__5);
				setState(234);
				identifier();
				setState(235);
				match(T__5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(239);
			match(T__2);
			setState(240);
			typeAssignRule();
			setState(241);
			match(SEMICOLUMN);
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
	 * The Class TypeAssignRuleContext.
	 */
	public static class TypeAssignRuleContext extends ParserRuleContext {
		
		/**
		 * Instantiates a new type assign rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public TypeAssignRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_typeAssignRule; }
	 
		/**
		 * Instantiates a new type assign rule context.
		 */
		public TypeAssignRuleContext() { }
		
		/**
		 * Copy from.
		 *
		 * @param ctx the ctx
		 */
		public void copyFrom(TypeAssignRuleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	
	/**
	 * The Class AssignBuiltInTypeWithArgsContext.
	 */
	public static class AssignBuiltInTypeWithArgsContext extends TypeAssignRuleContext {
		
		/** The ref. */
		public IdentifierContext ref;
		
		/** The integer. */
		public Token INTEGER;
		
		/** The args. */
		public List<Token> args = new ArrayList<Token>();
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Integer.
		 *
		 * @return the list
		 */
		public List<TerminalNode> INTEGER() { return getTokens(CdsParser.INTEGER); }
		
		/**
		 * Integer.
		 *
		 * @param i the i
		 * @return the terminal node
		 */
		public TerminalNode INTEGER(int i) {
			return getToken(CdsParser.INTEGER, i);
		}
		
		/**
		 * Instantiates a new assign built in type with args context.
		 *
		 * @param ctx the ctx
		 */
		public AssignBuiltInTypeWithArgsContext(TypeAssignRuleContext ctx) { copyFrom(ctx); }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterAssignBuiltInTypeWithArgs(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitAssignBuiltInTypeWithArgs(this);
		}
	}
	
	/**
	 * The Class AssignHanaTypeWithArgsContext.
	 */
	public static class AssignHanaTypeWithArgsContext extends TypeAssignRuleContext {
		
		/** The hana type. */
		public IdentifierContext hanaType;
		
		/** The integer. */
		public Token INTEGER;
		
		/** The args. */
		public List<Token> args = new ArrayList<Token>();
		
		/**
		 * Hana.
		 *
		 * @return the terminal node
		 */
		public TerminalNode HANA() { return getToken(CdsParser.HANA, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Integer.
		 *
		 * @return the list
		 */
		public List<TerminalNode> INTEGER() { return getTokens(CdsParser.INTEGER); }
		
		/**
		 * Integer.
		 *
		 * @param i the i
		 * @return the terminal node
		 */
		public TerminalNode INTEGER(int i) {
			return getToken(CdsParser.INTEGER, i);
		}
		
		/**
		 * Instantiates a new assign hana type with args context.
		 *
		 * @param ctx the ctx
		 */
		public AssignHanaTypeWithArgsContext(TypeAssignRuleContext ctx) { copyFrom(ctx); }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterAssignHanaTypeWithArgs(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitAssignHanaTypeWithArgs(this);
		}
	}
	
	/**
	 * The Class AssignHanaTypeContext.
	 */
	public static class AssignHanaTypeContext extends TypeAssignRuleContext {
		
		/** The hana type. */
		public IdentifierContext hanaType;
		
		/**
		 * Hana.
		 *
		 * @return the terminal node
		 */
		public TerminalNode HANA() { return getToken(CdsParser.HANA, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Instantiates a new assign hana type context.
		 *
		 * @param ctx the ctx
		 */
		public AssignHanaTypeContext(TypeAssignRuleContext ctx) { copyFrom(ctx); }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterAssignHanaType(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitAssignHanaType(this);
		}
	}
	
	/**
	 * The Class AssignTypeContext.
	 */
	public static class AssignTypeContext extends TypeAssignRuleContext {
		
		/** The identifier. */
		public IdentifierContext identifier;
		
		/** The path sub members. */
		public List<IdentifierContext> pathSubMembers = new ArrayList<IdentifierContext>();
		
		/**
		 * Identifier.
		 *
		 * @return the list
		 */
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		
		/**
		 * Identifier.
		 *
		 * @param i the i
		 * @return the identifier context
		 */
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		
		/**
		 * Type of.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TYPE_OF() { return getToken(CdsParser.TYPE_OF, 0); }
		
		/**
		 * Instantiates a new assign type context.
		 *
		 * @param ctx the ctx
		 */
		public AssignTypeContext(TypeAssignRuleContext ctx) { copyFrom(ctx); }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterAssignType(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitAssignType(this);
		}
	}

	/**
	 * Type assign rule.
	 *
	 * @return the type assign rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final TypeAssignRuleContext typeAssignRule() throws RecognitionException {
		TypeAssignRuleContext _localctx = new TypeAssignRuleContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_typeAssignRule);
		int _la;
		try {
			setState(283);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				_localctx = new AssignBuiltInTypeWithArgsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(243);
				((AssignBuiltInTypeWithArgsContext)_localctx).ref = identifier();
				setState(244);
				match(T__6);
				setState(245);
				((AssignBuiltInTypeWithArgsContext)_localctx).INTEGER = match(INTEGER);
				((AssignBuiltInTypeWithArgsContext)_localctx).args.add(((AssignBuiltInTypeWithArgsContext)_localctx).INTEGER);
				setState(250);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(246);
					match(T__7);
					setState(247);
					((AssignBuiltInTypeWithArgsContext)_localctx).INTEGER = match(INTEGER);
					((AssignBuiltInTypeWithArgsContext)_localctx).args.add(((AssignBuiltInTypeWithArgsContext)_localctx).INTEGER);
					}
					}
					setState(252);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(253);
				match(T__8);
				}
				break;
			case 2:
				_localctx = new AssignHanaTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(255);
				match(HANA);
				setState(256);
				match(T__0);
				setState(257);
				((AssignHanaTypeContext)_localctx).hanaType = identifier();
				}
				break;
			case 3:
				_localctx = new AssignHanaTypeWithArgsContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(258);
				match(HANA);
				setState(259);
				match(T__0);
				setState(260);
				((AssignHanaTypeWithArgsContext)_localctx).hanaType = identifier();
				setState(261);
				match(T__6);
				setState(262);
				((AssignHanaTypeWithArgsContext)_localctx).INTEGER = match(INTEGER);
				((AssignHanaTypeWithArgsContext)_localctx).args.add(((AssignHanaTypeWithArgsContext)_localctx).INTEGER);
				setState(267);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(263);
					match(T__7);
					setState(264);
					((AssignHanaTypeWithArgsContext)_localctx).INTEGER = match(INTEGER);
					((AssignHanaTypeWithArgsContext)_localctx).args.add(((AssignHanaTypeWithArgsContext)_localctx).INTEGER);
					}
					}
					setState(269);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(270);
				match(T__8);
				}
				break;
			case 4:
				_localctx = new AssignTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(273);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TYPE_OF) {
					{
					setState(272);
					match(TYPE_OF);
					}
				}

				setState(275);
				((AssignTypeContext)_localctx).identifier = identifier();
				((AssignTypeContext)_localctx).pathSubMembers.add(((AssignTypeContext)_localctx).identifier);
				setState(280);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(276);
					match(T__0);
					setState(277);
					((AssignTypeContext)_localctx).identifier = identifier();
					((AssignTypeContext)_localctx).pathSubMembers.add(((AssignTypeContext)_localctx).identifier);
					}
					}
					setState(282);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
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
	 * The Class ElementDeclRuleContext.
	 */
	public static class ElementDeclRuleContext extends ParserRuleContext {
		
		/** The key. */
		public IdentifierContext key;
		
		/** The name. */
		public IdentifierContext name;
		
		/**
		 * Type assign rule.
		 *
		 * @return the type assign rule context
		 */
		public TypeAssignRuleContext typeAssignRule() {
			return getRuleContext(TypeAssignRuleContext.class,0);
		}
		
		/**
		 * Semicolumn.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLUMN() { return getToken(CdsParser.SEMICOLUMN, 0); }
		
		/**
		 * Annotation rule.
		 *
		 * @return the list
		 */
		public List<AnnotationRuleContext> annotationRule() {
			return getRuleContexts(AnnotationRuleContext.class);
		}
		
		/**
		 * Annotation rule.
		 *
		 * @param i the i
		 * @return the annotation rule context
		 */
		public AnnotationRuleContext annotationRule(int i) {
			return getRuleContext(AnnotationRuleContext.class,i);
		}
		
		/**
		 * Identifier.
		 *
		 * @return the list
		 */
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		
		/**
		 * Identifier.
		 *
		 * @param i the i
		 * @return the identifier context
		 */
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		
		/**
		 * Element details.
		 *
		 * @return the list
		 */
		public List<ElementDetailsContext> elementDetails() {
			return getRuleContexts(ElementDetailsContext.class);
		}
		
		/**
		 * Element details.
		 *
		 * @param i the i
		 * @return the element details context
		 */
		public ElementDetailsContext elementDetails(int i) {
			return getRuleContext(ElementDetailsContext.class,i);
		}
		
		/**
		 * Instantiates a new element decl rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ElementDeclRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_elementDeclRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterElementDeclRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitElementDeclRule(this);
		}
	}

	/**
	 * Element decl rule.
	 *
	 * @return the element decl rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final ElementDeclRuleContext elementDeclRule() throws RecognitionException {
		ElementDeclRuleContext _localctx = new ElementDeclRuleContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_elementDeclRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(285);
				annotationRule();
				}
				}
				setState(290);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(292);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				{
				setState(291);
				((ElementDeclRuleContext)_localctx).key = identifier();
				}
				break;
			}
			setState(299);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAMESPACE:
			case AS:
			case ON:
			case SELECT:
			case FROM:
			case WHERE:
			case DEFINE:
			case UNION:
			case DISTINCT:
			case HANA:
			case JOIN_TYPES:
			case USING:
			case DEFAULT:
			case CONTEXT:
			case ENTITY:
			case VIEW:
			case TYPE:
			case ASSOCIATION:
			case TO:
			case ID:
			case STRING:
				{
				setState(294);
				((ElementDeclRuleContext)_localctx).name = identifier();
				}
				break;
			case T__5:
				{
				setState(295);
				match(T__5);
				setState(296);
				((ElementDeclRuleContext)_localctx).name = identifier();
				setState(297);
				match(T__5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(301);
			match(T__2);
			setState(302);
			typeAssignRule();
			setState(306);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << NULL) | (1L << DEFAULT))) != 0)) {
				{
				{
				setState(303);
				elementDetails();
				}
				}
				setState(308);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(309);
			match(SEMICOLUMN);
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
	 * The Class ElementDetailsContext.
	 */
	public static class ElementDetailsContext extends ParserRuleContext {
		
		/**
		 * Default value.
		 *
		 * @return the default value context
		 */
		public DefaultValueContext defaultValue() {
			return getRuleContext(DefaultValueContext.class,0);
		}
		
		/**
		 * Element constraints.
		 *
		 * @return the element constraints context
		 */
		public ElementConstraintsContext elementConstraints() {
			return getRuleContext(ElementConstraintsContext.class,0);
		}
		
		/**
		 * Instantiates a new element details context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ElementDetailsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_elementDetails; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterElementDetails(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitElementDetails(this);
		}
	}

	/**
	 * Element details.
	 *
	 * @return the element details context
	 * @throws RecognitionException the recognition exception
	 */
	public final ElementDetailsContext elementDetails() throws RecognitionException {
		ElementDetailsContext _localctx = new ElementDetailsContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_elementDetails);
		try {
			setState(313);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DEFAULT:
				enterOuterAlt(_localctx, 1);
				{
				setState(311);
				defaultValue();
				}
				break;
			case T__9:
			case T__10:
			case T__11:
			case NULL:
				enterOuterAlt(_localctx, 2);
				{
				setState(312);
				elementConstraints();
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
	 * The Class ElementConstraintsContext.
	 */
	public static class ElementConstraintsContext extends ParserRuleContext {
		
		/**
		 * Constraints.
		 *
		 * @return the constraints context
		 */
		public ConstraintsContext constraints() {
			return getRuleContext(ConstraintsContext.class,0);
		}
		
		/**
		 * Instantiates a new element constraints context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ElementConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_elementConstraints; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterElementConstraints(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitElementConstraints(this);
		}
	}

	/**
	 * Element constraints.
	 *
	 * @return the element constraints context
	 * @throws RecognitionException the recognition exception
	 */
	public final ElementConstraintsContext elementConstraints() throws RecognitionException {
		ElementConstraintsContext _localctx = new ElementConstraintsContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_elementConstraints);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			constraints();
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
	 * The Class AssociationConstraintsContext.
	 */
	public static class AssociationConstraintsContext extends ParserRuleContext {
		
		/**
		 * Constraints.
		 *
		 * @return the constraints context
		 */
		public ConstraintsContext constraints() {
			return getRuleContext(ConstraintsContext.class,0);
		}
		
		/**
		 * Instantiates a new association constraints context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public AssociationConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_associationConstraints; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterAssociationConstraints(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitAssociationConstraints(this);
		}
	}

	/**
	 * Association constraints.
	 *
	 * @return the association constraints context
	 * @throws RecognitionException the recognition exception
	 */
	public final AssociationConstraintsContext associationConstraints() throws RecognitionException {
		AssociationConstraintsContext _localctx = new AssociationConstraintsContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_associationConstraints);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			constraints();
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
	 * The Class ConstraintsContext.
	 */
	public static class ConstraintsContext extends ParserRuleContext {
		
		/**
		 * Null.
		 *
		 * @return the terminal node
		 */
		public TerminalNode NULL() { return getToken(CdsParser.NULL, 0); }
		
		/**
		 * Instantiates a new constraints context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_constraints; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterConstraints(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitConstraints(this);
		}
	}

	/**
	 * Constraints.
	 *
	 * @return the constraints context
	 * @throws RecognitionException the recognition exception
	 */
	public final ConstraintsContext constraints() throws RecognitionException {
		ConstraintsContext _localctx = new ConstraintsContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_constraints);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << NULL))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	 * The Class AssociationContext.
	 */
	public static class AssociationContext extends ParserRuleContext {
		
		/** The key. */
		public IdentifierContext key;
		
		/** The asc id. */
		public IdentifierContext ascId;
		
		/**
		 * Association.
		 *
		 * @return the terminal node
		 */
		public TerminalNode ASSOCIATION() { return getToken(CdsParser.ASSOCIATION, 0); }
		
		/**
		 * To.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TO() { return getToken(CdsParser.TO, 0); }
		
		/**
		 * Association target.
		 *
		 * @return the association target context
		 */
		public AssociationTargetContext associationTarget() {
			return getRuleContext(AssociationTargetContext.class,0);
		}
		
		/**
		 * Semicolumn.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLUMN() { return getToken(CdsParser.SEMICOLUMN, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the list
		 */
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		
		/**
		 * Identifier.
		 *
		 * @param i the i
		 * @return the identifier context
		 */
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		
		/**
		 * Cardinality.
		 *
		 * @return the cardinality context
		 */
		public CardinalityContext cardinality() {
			return getRuleContext(CardinalityContext.class,0);
		}
		
		/**
		 * Managed foreign keys.
		 *
		 * @return the list
		 */
		public List<ManagedForeignKeysContext> managedForeignKeys() {
			return getRuleContexts(ManagedForeignKeysContext.class);
		}
		
		/**
		 * Managed foreign keys.
		 *
		 * @param i the i
		 * @return the managed foreign keys context
		 */
		public ManagedForeignKeysContext managedForeignKeys(int i) {
			return getRuleContext(ManagedForeignKeysContext.class,i);
		}
		
		/**
		 * Unmanaged foreign key.
		 *
		 * @return the list
		 */
		public List<UnmanagedForeignKeyContext> unmanagedForeignKey() {
			return getRuleContexts(UnmanagedForeignKeyContext.class);
		}
		
		/**
		 * Unmanaged foreign key.
		 *
		 * @param i the i
		 * @return the unmanaged foreign key context
		 */
		public UnmanagedForeignKeyContext unmanagedForeignKey(int i) {
			return getRuleContext(UnmanagedForeignKeyContext.class,i);
		}
		
		/**
		 * Association constraints.
		 *
		 * @return the association constraints context
		 */
		public AssociationConstraintsContext associationConstraints() {
			return getRuleContext(AssociationConstraintsContext.class,0);
		}
		
		/**
		 * Instantiates a new association context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public AssociationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_association; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterAssociation(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitAssociation(this);
		}
	}

	/**
	 * Association.
	 *
	 * @return the association context
	 * @throws RecognitionException the recognition exception
	 */
	public final AssociationContext association() throws RecognitionException {
		AssociationContext _localctx = new AssociationContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_association);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(321);
				((AssociationContext)_localctx).key = identifier();
				}
				break;
			}
			setState(324);
			((AssociationContext)_localctx).ascId = identifier();
			setState(325);
			match(T__2);
			setState(326);
			match(ASSOCIATION);
			setState(328);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__13) {
				{
				setState(327);
				cardinality();
				}
			}

			setState(330);
			match(TO);
			setState(331);
			associationTarget();
			setState(336);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3 || _la==ON) {
				{
				setState(334);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__3:
					{
					setState(332);
					managedForeignKeys();
					}
					break;
				case ON:
					{
					setState(333);
					unmanagedForeignKey();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(338);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(340);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << NULL))) != 0)) {
				{
				setState(339);
				associationConstraints();
				}
			}

			setState(342);
			match(SEMICOLUMN);
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
	 * The Class CalculatedAssociationContext.
	 */
	public static class CalculatedAssociationContext extends ParserRuleContext {
		
		/** The asc id. */
		public IdentifierContext ascId;
		
		/**
		 * Type assign rule.
		 *
		 * @return the type assign rule context
		 */
		public TypeAssignRuleContext typeAssignRule() {
			return getRuleContext(TypeAssignRuleContext.class,0);
		}
		
		/**
		 * Statement.
		 *
		 * @return the statement context
		 */
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		
		/**
		 * Semicolumn.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLUMN() { return getToken(CdsParser.SEMICOLUMN, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Element details.
		 *
		 * @return the list
		 */
		public List<ElementDetailsContext> elementDetails() {
			return getRuleContexts(ElementDetailsContext.class);
		}
		
		/**
		 * Element details.
		 *
		 * @param i the i
		 * @return the element details context
		 */
		public ElementDetailsContext elementDetails(int i) {
			return getRuleContext(ElementDetailsContext.class,i);
		}
		
		/**
		 * Instantiates a new calculated association context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public CalculatedAssociationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_calculatedAssociation; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterCalculatedAssociation(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitCalculatedAssociation(this);
		}
	}

	/**
	 * Calculated association.
	 *
	 * @return the calculated association context
	 * @throws RecognitionException the recognition exception
	 */
	public final CalculatedAssociationContext calculatedAssociation() throws RecognitionException {
		CalculatedAssociationContext _localctx = new CalculatedAssociationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_calculatedAssociation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344);
			((CalculatedAssociationContext)_localctx).ascId = identifier();
			setState(345);
			match(T__2);
			setState(346);
			typeAssignRule();
			setState(350);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << NULL) | (1L << DEFAULT))) != 0)) {
				{
				{
				setState(347);
				elementDetails();
				}
				}
				setState(352);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(353);
			match(T__12);
			setState(354);
			statement();
			setState(355);
			match(SEMICOLUMN);
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
	 * The Class StatementContext.
	 */
	public static class StatementContext extends ParserRuleContext {
		
		/**
		 * Instantiates a new statement context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_statement; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterStatement(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitStatement(this);
		}
	}

	/**
	 * Statement.
	 *
	 * @return the statement context
	 * @throws RecognitionException the recognition exception
	 */
	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_statement);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(357);
					matchWildcard();
					}
					} 
				}
				setState(362);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			}
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
	 * The Class AssociationTargetContext.
	 */
	public static class AssociationTargetContext extends ParserRuleContext {
		
		/** The identifier. */
		public IdentifierContext identifier;
		
		/** The path sub members. */
		public List<IdentifierContext> pathSubMembers = new ArrayList<IdentifierContext>();
		
		/**
		 * Identifier.
		 *
		 * @return the list
		 */
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		
		/**
		 * Identifier.
		 *
		 * @param i the i
		 * @return the identifier context
		 */
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		
		/**
		 * Instantiates a new association target context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public AssociationTargetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_associationTarget; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterAssociationTarget(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitAssociationTarget(this);
		}
	}

	/**
	 * Association target.
	 *
	 * @return the association target context
	 * @throws RecognitionException the recognition exception
	 */
	public final AssociationTargetContext associationTarget() throws RecognitionException {
		AssociationTargetContext _localctx = new AssociationTargetContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_associationTarget);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			((AssociationTargetContext)_localctx).identifier = identifier();
			((AssociationTargetContext)_localctx).pathSubMembers.add(((AssociationTargetContext)_localctx).identifier);
			setState(368);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(364);
				match(T__0);
				setState(365);
				((AssociationTargetContext)_localctx).identifier = identifier();
				((AssociationTargetContext)_localctx).pathSubMembers.add(((AssociationTargetContext)_localctx).identifier);
				}
				}
				setState(370);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	 * The Class UnmanagedForeignKeyContext.
	 */
	public static class UnmanagedForeignKeyContext extends ParserRuleContext {
		
		/** The identifier. */
		public IdentifierContext identifier;
		
		/** The path sub members. */
		public List<IdentifierContext> pathSubMembers = new ArrayList<IdentifierContext>();
		
		/** The source. */
		public IdentifierContext source;
		
		/**
		 * On.
		 *
		 * @return the terminal node
		 */
		public TerminalNode ON() { return getToken(CdsParser.ON, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the list
		 */
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		
		/**
		 * Identifier.
		 *
		 * @param i the i
		 * @return the identifier context
		 */
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		
		/**
		 * Instantiates a new unmanaged foreign key context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public UnmanagedForeignKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_unmanagedForeignKey; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterUnmanagedForeignKey(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitUnmanagedForeignKey(this);
		}
	}

	/**
	 * Unmanaged foreign key.
	 *
	 * @return the unmanaged foreign key context
	 * @throws RecognitionException the recognition exception
	 */
	public final UnmanagedForeignKeyContext unmanagedForeignKey() throws RecognitionException {
		UnmanagedForeignKeyContext _localctx = new UnmanagedForeignKeyContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_unmanagedForeignKey);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(371);
			match(ON);
			setState(372);
			((UnmanagedForeignKeyContext)_localctx).identifier = identifier();
			((UnmanagedForeignKeyContext)_localctx).pathSubMembers.add(((UnmanagedForeignKeyContext)_localctx).identifier);
			setState(377);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(373);
				match(T__0);
				setState(374);
				((UnmanagedForeignKeyContext)_localctx).identifier = identifier();
				((UnmanagedForeignKeyContext)_localctx).pathSubMembers.add(((UnmanagedForeignKeyContext)_localctx).identifier);
				}
				}
				setState(379);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(380);
			match(T__12);
			setState(381);
			((UnmanagedForeignKeyContext)_localctx).source = identifier();
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
	 * The Class ManagedForeignKeysContext.
	 */
	public static class ManagedForeignKeysContext extends ParserRuleContext {
		
		/**
		 * Foreign key.
		 *
		 * @return the list
		 */
		public List<ForeignKeyContext> foreignKey() {
			return getRuleContexts(ForeignKeyContext.class);
		}
		
		/**
		 * Foreign key.
		 *
		 * @param i the i
		 * @return the foreign key context
		 */
		public ForeignKeyContext foreignKey(int i) {
			return getRuleContext(ForeignKeyContext.class,i);
		}
		
		/**
		 * Instantiates a new managed foreign keys context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ManagedForeignKeysContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_managedForeignKeys; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterManagedForeignKeys(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitManagedForeignKeys(this);
		}
	}

	/**
	 * Managed foreign keys.
	 *
	 * @return the managed foreign keys context
	 * @throws RecognitionException the recognition exception
	 */
	public final ManagedForeignKeysContext managedForeignKeys() throws RecognitionException {
		ManagedForeignKeysContext _localctx = new ManagedForeignKeysContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_managedForeignKeys);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(383);
			match(T__3);
			setState(384);
			foreignKey();
			setState(389);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(385);
				match(T__7);
				setState(386);
				foreignKey();
				}
				}
				setState(391);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(392);
			match(T__4);
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
	 * The Class ForeignKeyContext.
	 */
	public static class ForeignKeyContext extends ParserRuleContext {
		
		/** The identifier. */
		public IdentifierContext identifier;
		
		/** The path sub members. */
		public List<IdentifierContext> pathSubMembers = new ArrayList<IdentifierContext>();
		
		/** The alias. */
		public IdentifierContext alias;
		
		/**
		 * Identifier.
		 *
		 * @return the list
		 */
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		
		/**
		 * Identifier.
		 *
		 * @param i the i
		 * @return the identifier context
		 */
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		
		/**
		 * As.
		 *
		 * @return the terminal node
		 */
		public TerminalNode AS() { return getToken(CdsParser.AS, 0); }
		
		/**
		 * Instantiates a new foreign key context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ForeignKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_foreignKey; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterForeignKey(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitForeignKey(this);
		}
	}

	/**
	 * Foreign key.
	 *
	 * @return the foreign key context
	 * @throws RecognitionException the recognition exception
	 */
	public final ForeignKeyContext foreignKey() throws RecognitionException {
		ForeignKeyContext _localctx = new ForeignKeyContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_foreignKey);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(394);
			((ForeignKeyContext)_localctx).identifier = identifier();
			((ForeignKeyContext)_localctx).pathSubMembers.add(((ForeignKeyContext)_localctx).identifier);
			setState(399);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(395);
				match(T__0);
				setState(396);
				((ForeignKeyContext)_localctx).identifier = identifier();
				((ForeignKeyContext)_localctx).pathSubMembers.add(((ForeignKeyContext)_localctx).identifier);
				}
				}
				setState(401);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(404);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(402);
				match(AS);
				setState(403);
				((ForeignKeyContext)_localctx).alias = identifier();
				}
			}

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
	 * The Class CardinalityContext.
	 */
	public static class CardinalityContext extends ParserRuleContext {
		
		/**
		 * Instantiates a new cardinality context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public CardinalityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_cardinality; }
	 
		/**
		 * Instantiates a new cardinality context.
		 */
		public CardinalityContext() { }
		
		/**
		 * Copy from.
		 *
		 * @param ctx the ctx
		 */
		public void copyFrom(CardinalityContext ctx) {
			super.copyFrom(ctx);
		}
	}
	
	/**
	 * The Class NoCardinalityContext.
	 */
	public static class NoCardinalityContext extends CardinalityContext {
		
		/**
		 * Instantiates a new no cardinality context.
		 *
		 * @param ctx the ctx
		 */
		public NoCardinalityContext(CardinalityContext ctx) { copyFrom(ctx); }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterNoCardinality(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitNoCardinality(this);
		}
	}
	
	/**
	 * The Class MaxCardinalityContext.
	 */
	public static class MaxCardinalityContext extends CardinalityContext {
		
		/** The max. */
		public Token max;
		
		/** The many. */
		public Token many;
		
		/**
		 * Integer.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INTEGER() { return getToken(CdsParser.INTEGER, 0); }
		
		/**
		 * Instantiates a new max cardinality context.
		 *
		 * @param ctx the ctx
		 */
		public MaxCardinalityContext(CardinalityContext ctx) { copyFrom(ctx); }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterMaxCardinality(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitMaxCardinality(this);
		}
	}
	
	/**
	 * The Class MinMaxCardinalityContext.
	 */
	public static class MinMaxCardinalityContext extends CardinalityContext {
		
		/** The max. */
		public Token max;
		
		/** The many. */
		public Token many;
		
		/**
		 * Association min.
		 *
		 * @return the terminal node
		 */
		public TerminalNode ASSOCIATION_MIN() { return getToken(CdsParser.ASSOCIATION_MIN, 0); }
		
		/**
		 * Integer.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INTEGER() { return getToken(CdsParser.INTEGER, 0); }
		
		/**
		 * Instantiates a new min max cardinality context.
		 *
		 * @param ctx the ctx
		 */
		public MinMaxCardinalityContext(CardinalityContext ctx) { copyFrom(ctx); }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterMinMaxCardinality(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitMinMaxCardinality(this);
		}
	}

	/**
	 * Cardinality.
	 *
	 * @return the cardinality context
	 * @throws RecognitionException the recognition exception
	 */
	public final CardinalityContext cardinality() throws RecognitionException {
		CardinalityContext _localctx = new CardinalityContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_cardinality);
		try {
			setState(421);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,52,_ctx) ) {
			case 1:
				_localctx = new MinMaxCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(406);
				match(T__13);
				setState(407);
				match(ASSOCIATION_MIN);
				setState(410);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case INTEGER:
					{
					setState(408);
					((MinMaxCardinalityContext)_localctx).max = match(INTEGER);
					}
					break;
				case T__14:
					{
					setState(409);
					((MinMaxCardinalityContext)_localctx).many = match(T__14);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(412);
				match(T__15);
				}
				break;
			case 2:
				_localctx = new MaxCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(413);
				match(T__13);
				setState(416);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case INTEGER:
					{
					setState(414);
					((MaxCardinalityContext)_localctx).max = match(INTEGER);
					}
					break;
				case T__14:
					{
					setState(415);
					((MaxCardinalityContext)_localctx).many = match(T__14);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(418);
				match(T__15);
				}
				break;
			case 3:
				_localctx = new NoCardinalityContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(419);
				match(T__13);
				setState(420);
				match(T__15);
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
	 * The Class DefaultValueContext.
	 */
	public static class DefaultValueContext extends ParserRuleContext {
		
		/** The value. */
		public Token value;
		
		/**
		 * Default.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DEFAULT() { return getToken(CdsParser.DEFAULT, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(CdsParser.STRING, 0); }
		
		/**
		 * Integer.
		 *
		 * @return the terminal node
		 */
		public TerminalNode INTEGER() { return getToken(CdsParser.INTEGER, 0); }
		
		/**
		 * Decimal.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DECIMAL() { return getToken(CdsParser.DECIMAL, 0); }
		
		/**
		 * Boolean.
		 *
		 * @return the terminal node
		 */
		public TerminalNode BOOLEAN() { return getToken(CdsParser.BOOLEAN, 0); }
		
		/**
		 * Local time.
		 *
		 * @return the terminal node
		 */
		public TerminalNode LOCAL_TIME() { return getToken(CdsParser.LOCAL_TIME, 0); }
		
		/**
		 * Local date.
		 *
		 * @return the terminal node
		 */
		public TerminalNode LOCAL_DATE() { return getToken(CdsParser.LOCAL_DATE, 0); }
		
		/**
		 * Utc date time.
		 *
		 * @return the terminal node
		 */
		public TerminalNode UTC_DATE_TIME() { return getToken(CdsParser.UTC_DATE_TIME, 0); }
		
		/**
		 * Utc timestamp.
		 *
		 * @return the terminal node
		 */
		public TerminalNode UTC_TIMESTAMP() { return getToken(CdsParser.UTC_TIMESTAMP, 0); }
		
		/**
		 * Varbinary.
		 *
		 * @return the terminal node
		 */
		public TerminalNode VARBINARY() { return getToken(CdsParser.VARBINARY, 0); }
		
		/**
		 * Datetime value function.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DATETIME_VALUE_FUNCTION() { return getToken(CdsParser.DATETIME_VALUE_FUNCTION, 0); }
		
		/**
		 * Null.
		 *
		 * @return the terminal node
		 */
		public TerminalNode NULL() { return getToken(CdsParser.NULL, 0); }
		
		/**
		 * Instantiates a new default value context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public DefaultValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_defaultValue; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterDefaultValue(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitDefaultValue(this);
		}
	}

	/**
	 * Default value.
	 *
	 * @return the default value context
	 * @throws RecognitionException the recognition exception
	 */
	public final DefaultValueContext defaultValue() throws RecognitionException {
		DefaultValueContext _localctx = new DefaultValueContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_defaultValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(423);
			match(DEFAULT);
			setState(424);
			((DefaultValueContext)_localctx).value = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DATETIME_VALUE_FUNCTION) | (1L << NULL) | (1L << BOOLEAN) | (1L << INTEGER) | (1L << DECIMAL) | (1L << LOCAL_TIME) | (1L << LOCAL_DATE) | (1L << UTC_DATE_TIME) | (1L << UTC_TIMESTAMP) | (1L << STRING) | (1L << VARBINARY))) != 0)) ) {
				((DefaultValueContext)_localctx).value = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	 * The Class AnnotationRuleContext.
	 */
	public static class AnnotationRuleContext extends ParserRuleContext {
		
		/**
		 * Instantiates a new annotation rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public AnnotationRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_annotationRule; }
	 
		/**
		 * Instantiates a new annotation rule context.
		 */
		public AnnotationRuleContext() { }
		
		/**
		 * Copy from.
		 *
		 * @param ctx the ctx
		 */
		public void copyFrom(AnnotationRuleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	
	/**
	 * The Class AnnPropertyRuleContext.
	 */
	public static class AnnPropertyRuleContext extends AnnotationRuleContext {
		
		/** The ann id. */
		public IdentifierContext annId;
		
		/** The prop. */
		public IdentifierContext prop;
		
		/**
		 * Ann value.
		 *
		 * @return the ann value context
		 */
		public AnnValueContext annValue() {
			return getRuleContext(AnnValueContext.class,0);
		}
		
		/**
		 * Identifier.
		 *
		 * @return the list
		 */
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		
		/**
		 * Identifier.
		 *
		 * @param i the i
		 * @return the identifier context
		 */
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		
		/**
		 * Instantiates a new ann property rule context.
		 *
		 * @param ctx the ctx
		 */
		public AnnPropertyRuleContext(AnnotationRuleContext ctx) { copyFrom(ctx); }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterAnnPropertyRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitAnnPropertyRule(this);
		}
	}
	
	/**
	 * The Class AnnObjectRuleContext.
	 */
	public static class AnnObjectRuleContext extends AnnotationRuleContext {
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Ann value.
		 *
		 * @return the ann value context
		 */
		public AnnValueContext annValue() {
			return getRuleContext(AnnValueContext.class,0);
		}
		
		/**
		 * Instantiates a new ann object rule context.
		 *
		 * @param ctx the ctx
		 */
		public AnnObjectRuleContext(AnnotationRuleContext ctx) { copyFrom(ctx); }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterAnnObjectRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitAnnObjectRule(this);
		}
	}
	
	/**
	 * The Class AnnMarkerRuleContext.
	 */
	public static class AnnMarkerRuleContext extends AnnotationRuleContext {
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Instantiates a new ann marker rule context.
		 *
		 * @param ctx the ctx
		 */
		public AnnMarkerRuleContext(AnnotationRuleContext ctx) { copyFrom(ctx); }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterAnnMarkerRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitAnnMarkerRule(this);
		}
	}

	/**
	 * Annotation rule.
	 *
	 * @return the annotation rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final AnnotationRuleContext annotationRule() throws RecognitionException {
		AnnotationRuleContext _localctx = new AnnotationRuleContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_annotationRule);
		int _la;
		try {
			setState(445);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				_localctx = new AnnObjectRuleContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(426);
				match(T__16);
				setState(427);
				identifier();
				setState(428);
				match(T__2);
				setState(429);
				annValue();
				}
				break;
			case 2:
				_localctx = new AnnPropertyRuleContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(431);
				match(T__16);
				setState(432);
				((AnnPropertyRuleContext)_localctx).annId = identifier();
				setState(437);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(433);
					match(T__0);
					setState(434);
					((AnnPropertyRuleContext)_localctx).prop = identifier();
					}
					}
					setState(439);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(440);
				match(T__2);
				setState(441);
				annValue();
				}
				break;
			case 3:
				_localctx = new AnnMarkerRuleContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(443);
				match(T__16);
				setState(444);
				identifier();
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
	 * The Class AnnValueContext.
	 */
	public static class AnnValueContext extends ParserRuleContext {
		
		/** The literal. */
		public Token literal;
		
		/**
		 * Arr rule.
		 *
		 * @return the arr rule context
		 */
		public ArrRuleContext arrRule() {
			return getRuleContext(ArrRuleContext.class,0);
		}
		
		/**
		 * Enum rule.
		 *
		 * @return the enum rule context
		 */
		public EnumRuleContext enumRule() {
			return getRuleContext(EnumRuleContext.class,0);
		}
		
		/**
		 * Obj.
		 *
		 * @return the obj context
		 */
		public ObjContext obj() {
			return getRuleContext(ObjContext.class,0);
		}
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(CdsParser.STRING, 0); }
		
		/**
		 * Boolean.
		 *
		 * @return the terminal node
		 */
		public TerminalNode BOOLEAN() { return getToken(CdsParser.BOOLEAN, 0); }
		
		/**
		 * Instantiates a new ann value context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public AnnValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_annValue; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterAnnValue(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitAnnValue(this);
		}
	}

	/**
	 * Ann value.
	 *
	 * @return the ann value context
	 * @throws RecognitionException the recognition exception
	 */
	public final AnnValueContext annValue() throws RecognitionException {
		AnnValueContext _localctx = new AnnValueContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_annValue);
		int _la;
		try {
			setState(451);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__13:
				enterOuterAlt(_localctx, 1);
				{
				setState(447);
				arrRule();
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 2);
				{
				setState(448);
				enumRule();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 3);
				{
				setState(449);
				obj();
				}
				break;
			case BOOLEAN:
			case STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(450);
				((AnnValueContext)_localctx).literal = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==BOOLEAN || _la==STRING) ) {
					((AnnValueContext)_localctx).literal = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
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
	 * The Class EnumRuleContext.
	 */
	public static class EnumRuleContext extends ParserRuleContext {
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Instantiates a new enum rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public EnumRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_enumRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterEnumRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitEnumRule(this);
		}
	}

	/**
	 * Enum rule.
	 *
	 * @return the enum rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final EnumRuleContext enumRule() throws RecognitionException {
		EnumRuleContext _localctx = new EnumRuleContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_enumRule);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(453);
			match(T__17);
			setState(454);
			identifier();
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
	 * The Class ArrRuleContext.
	 */
	public static class ArrRuleContext extends ParserRuleContext {
		
		/**
		 * Ann value.
		 *
		 * @return the list
		 */
		public List<AnnValueContext> annValue() {
			return getRuleContexts(AnnValueContext.class);
		}
		
		/**
		 * Ann value.
		 *
		 * @param i the i
		 * @return the ann value context
		 */
		public AnnValueContext annValue(int i) {
			return getRuleContext(AnnValueContext.class,i);
		}
		
		/**
		 * Instantiates a new arr rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ArrRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_arrRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterArrRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitArrRule(this);
		}
	}

	/**
	 * Arr rule.
	 *
	 * @return the arr rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final ArrRuleContext arrRule() throws RecognitionException {
		ArrRuleContext _localctx = new ArrRuleContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_arrRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(456);
			match(T__13);
			setState(457);
			annValue();
			setState(462);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(458);
				match(T__7);
				setState(459);
				annValue();
				}
				}
				setState(464);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(465);
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
	 * The Class ObjContext.
	 */
	public static class ObjContext extends ParserRuleContext {
		
		/**
		 * Key value.
		 *
		 * @return the list
		 */
		public List<KeyValueContext> keyValue() {
			return getRuleContexts(KeyValueContext.class);
		}
		
		/**
		 * Key value.
		 *
		 * @param i the i
		 * @return the key value context
		 */
		public KeyValueContext keyValue(int i) {
			return getRuleContext(KeyValueContext.class,i);
		}
		
		/**
		 * Instantiates a new obj context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public ObjContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_obj; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterObj(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitObj(this);
		}
	}

	/**
	 * Obj.
	 *
	 * @return the obj context
	 * @throws RecognitionException the recognition exception
	 */
	public final ObjContext obj() throws RecognitionException {
		ObjContext _localctx = new ObjContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_obj);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(467);
			match(T__3);
			setState(468);
			keyValue();
			setState(473);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(469);
				match(T__7);
				setState(470);
				keyValue();
				}
				}
				setState(475);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(476);
			match(T__4);
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
	 * The Class KeyValueContext.
	 */
	public static class KeyValueContext extends ParserRuleContext {
		
		/**
		 * Identifier.
		 *
		 * @return the identifier context
		 */
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		
		/**
		 * Ann value.
		 *
		 * @return the ann value context
		 */
		public AnnValueContext annValue() {
			return getRuleContext(AnnValueContext.class,0);
		}
		
		/**
		 * Instantiates a new key value context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public KeyValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_keyValue; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterKeyValue(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitKeyValue(this);
		}
	}

	/**
	 * Key value.
	 *
	 * @return the key value context
	 * @throws RecognitionException the recognition exception
	 */
	public final KeyValueContext keyValue() throws RecognitionException {
		KeyValueContext _localctx = new KeyValueContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_keyValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(478);
			identifier();
			setState(479);
			match(T__2);
			setState(480);
			annValue();
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
	 * The Class SelectRuleContext.
	 */
	public static class SelectRuleContext extends ParserRuleContext {
		
		/** The is union. */
		public Token isUnion;
		
		/** The identifier. */
		public IdentifierContext identifier;
		
		/** The depends on table. */
		public List<IdentifierContext> dependsOnTable = new ArrayList<IdentifierContext>();
		
		/** The depending table alias. */
		public IdentifierContext dependingTableAlias;
		
		/** The is distinct. */
		public Token isDistinct;
		
		/**
		 * Select.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SELECT() { return getToken(CdsParser.SELECT, 0); }
		
		/**
		 * From.
		 *
		 * @return the terminal node
		 */
		public TerminalNode FROM() { return getToken(CdsParser.FROM, 0); }
		
		/**
		 * Selected columns rule.
		 *
		 * @return the selected columns rule context
		 */
		public SelectedColumnsRuleContext selectedColumnsRule() {
			return getRuleContext(SelectedColumnsRuleContext.class,0);
		}
		
		/**
		 * Identifier.
		 *
		 * @return the list
		 */
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		
		/**
		 * Identifier.
		 *
		 * @param i the i
		 * @return the identifier context
		 */
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		
		/**
		 * Join rule.
		 *
		 * @return the list
		 */
		public List<JoinRuleContext> joinRule() {
			return getRuleContexts(JoinRuleContext.class);
		}
		
		/**
		 * Join rule.
		 *
		 * @param i the i
		 * @return the join rule context
		 */
		public JoinRuleContext joinRule(int i) {
			return getRuleContext(JoinRuleContext.class,i);
		}
		
		/**
		 * Semicolumn.
		 *
		 * @return the list
		 */
		public List<TerminalNode> SEMICOLUMN() { return getTokens(CdsParser.SEMICOLUMN); }
		
		/**
		 * Semicolumn.
		 *
		 * @param i the i
		 * @return the terminal node
		 */
		public TerminalNode SEMICOLUMN(int i) {
			return getToken(CdsParser.SEMICOLUMN, i);
		}
		
		/**
		 * Where.
		 *
		 * @return the terminal node
		 */
		public TerminalNode WHERE() { return getToken(CdsParser.WHERE, 0); }
		
		/**
		 * Where rule.
		 *
		 * @return the where rule context
		 */
		public WhereRuleContext whereRule() {
			return getRuleContext(WhereRuleContext.class,0);
		}
		
		/**
		 * Union.
		 *
		 * @return the terminal node
		 */
		public TerminalNode UNION() { return getToken(CdsParser.UNION, 0); }
		
		/**
		 * Distinct.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DISTINCT() { return getToken(CdsParser.DISTINCT, 0); }
		
		/**
		 * As.
		 *
		 * @return the terminal node
		 */
		public TerminalNode AS() { return getToken(CdsParser.AS, 0); }
		
		/**
		 * Instantiates a new select rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public SelectRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_selectRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterSelectRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitSelectRule(this);
		}
	}

	/**
	 * Select rule.
	 *
	 * @return the select rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final SelectRuleContext selectRule() throws RecognitionException {
		SelectRuleContext _localctx = new SelectRuleContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_selectRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(483);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==UNION) {
				{
				setState(482);
				((SelectRuleContext)_localctx).isUnion = match(UNION);
				}
			}

			setState(485);
			match(SELECT);
			setState(486);
			match(FROM);
			setState(487);
			((SelectRuleContext)_localctx).identifier = identifier();
			((SelectRuleContext)_localctx).dependsOnTable.add(((SelectRuleContext)_localctx).identifier);
			setState(492);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(488);
				match(T__0);
				setState(489);
				((SelectRuleContext)_localctx).identifier = identifier();
				((SelectRuleContext)_localctx).dependsOnTable.add(((SelectRuleContext)_localctx).identifier);
				}
				}
				setState(494);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(498);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
			case 1:
				{
				{
				setState(495);
				match(AS);
				setState(496);
				((SelectRuleContext)_localctx).dependingTableAlias = identifier();
				}
				}
				break;
			case 2:
				{
				setState(497);
				((SelectRuleContext)_localctx).dependingTableAlias = identifier();
				}
				break;
			}
			setState(503);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==JOIN_TYPES) {
				{
				{
				setState(500);
				joinRule();
				}
				}
				setState(505);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(507);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DISTINCT) {
				{
				setState(506);
				((SelectRuleContext)_localctx).isDistinct = match(DISTINCT);
				}
			}

			setState(509);
			match(T__3);
			setState(510);
			selectedColumnsRule();
			setState(511);
			match(T__4);
			setState(513);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLUMN) {
				{
				setState(512);
				match(SEMICOLUMN);
				}
			}

			setState(520);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(515);
				match(WHERE);
				setState(516);
				whereRule();
				setState(518);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMICOLUMN) {
					{
					setState(517);
					match(SEMICOLUMN);
					}
				}

				}
			}

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
	 * The Class JoinRuleContext.
	 */
	public static class JoinRuleContext extends ParserRuleContext {
		
		/** The join type. */
		public Token joinType;
		
		/** The join artifact name. */
		public IdentifierContext joinArtifactName;
		
		/** The join table alias. */
		public IdentifierContext joinTableAlias;
		
		/**
		 * Join fields.
		 *
		 * @return the join fields context
		 */
		public JoinFieldsContext joinFields() {
			return getRuleContext(JoinFieldsContext.class,0);
		}
		
		/**
		 * Join types.
		 *
		 * @return the terminal node
		 */
		public TerminalNode JOIN_TYPES() { return getToken(CdsParser.JOIN_TYPES, 0); }
		
		/**
		 * Identifier.
		 *
		 * @return the list
		 */
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		
		/**
		 * Identifier.
		 *
		 * @param i the i
		 * @return the identifier context
		 */
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		
		/**
		 * As.
		 *
		 * @return the terminal node
		 */
		public TerminalNode AS() { return getToken(CdsParser.AS, 0); }
		
		/**
		 * Instantiates a new join rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public JoinRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_joinRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterJoinRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitJoinRule(this);
		}
	}

	/**
	 * Join rule.
	 *
	 * @return the join rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final JoinRuleContext joinRule() throws RecognitionException {
		JoinRuleContext _localctx = new JoinRuleContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_joinRule);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(522);
			((JoinRuleContext)_localctx).joinType = match(JOIN_TYPES);
			setState(523);
			((JoinRuleContext)_localctx).joinArtifactName = identifier();
			setState(527);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				{
				{
				setState(524);
				match(AS);
				setState(525);
				((JoinRuleContext)_localctx).joinTableAlias = identifier();
				}
				}
				break;
			case 2:
				{
				setState(526);
				((JoinRuleContext)_localctx).joinTableAlias = identifier();
				}
				break;
			}
			setState(529);
			joinFields();
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
	 * The Class JoinFieldsContext.
	 */
	public static class JoinFieldsContext extends ParserRuleContext {
		
		/**
		 * Instantiates a new join fields context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public JoinFieldsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_joinFields; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterJoinFields(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitJoinFields(this);
		}
	}

	/**
	 * Join fields.
	 *
	 * @return the join fields context
	 * @throws RecognitionException the recognition exception
	 */
	public final JoinFieldsContext joinFields() throws RecognitionException {
		JoinFieldsContext _localctx = new JoinFieldsContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_joinFields);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(534);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(531);
					matchWildcard();
					}
					} 
				}
				setState(536);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
			}
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
	 * The Class SelectedColumnsRuleContext.
	 */
	public static class SelectedColumnsRuleContext extends ParserRuleContext {
		
		/**
		 * Instantiates a new selected columns rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public SelectedColumnsRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_selectedColumnsRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterSelectedColumnsRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitSelectedColumnsRule(this);
		}
	}

	/**
	 * Selected columns rule.
	 *
	 * @return the selected columns rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final SelectedColumnsRuleContext selectedColumnsRule() throws RecognitionException {
		SelectedColumnsRuleContext _localctx = new SelectedColumnsRuleContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_selectedColumnsRule);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(540);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,68,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(537);
					matchWildcard();
					}
					} 
				}
				setState(542);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,68,_ctx);
			}
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
	 * The Class WhereRuleContext.
	 */
	public static class WhereRuleContext extends ParserRuleContext {
		
		/**
		 * Instantiates a new where rule context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public WhereRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_whereRule; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterWhereRule(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitWhereRule(this);
		}
	}

	/**
	 * Where rule.
	 *
	 * @return the where rule context
	 * @throws RecognitionException the recognition exception
	 */
	public final WhereRuleContext whereRule() throws RecognitionException {
		WhereRuleContext _localctx = new WhereRuleContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_whereRule);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(546);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(543);
					matchWildcard();
					}
					} 
				}
				setState(548);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
			}
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
	 * The Class IdentifierContext.
	 */
	public static class IdentifierContext extends ParserRuleContext {
		
		/**
		 * Id.
		 *
		 * @return the terminal node
		 */
		public TerminalNode ID() { return getToken(CdsParser.ID, 0); }
		
		/**
		 * String.
		 *
		 * @return the terminal node
		 */
		public TerminalNode STRING() { return getToken(CdsParser.STRING, 0); }
		
		/**
		 * Namespace.
		 *
		 * @return the terminal node
		 */
		public TerminalNode NAMESPACE() { return getToken(CdsParser.NAMESPACE, 0); }
		
		/**
		 * Hana.
		 *
		 * @return the terminal node
		 */
		public TerminalNode HANA() { return getToken(CdsParser.HANA, 0); }
		
		/**
		 * As.
		 *
		 * @return the terminal node
		 */
		public TerminalNode AS() { return getToken(CdsParser.AS, 0); }
		
		/**
		 * On.
		 *
		 * @return the terminal node
		 */
		public TerminalNode ON() { return getToken(CdsParser.ON, 0); }
		
		/**
		 * Select.
		 *
		 * @return the terminal node
		 */
		public TerminalNode SELECT() { return getToken(CdsParser.SELECT, 0); }
		
		/**
		 * From.
		 *
		 * @return the terminal node
		 */
		public TerminalNode FROM() { return getToken(CdsParser.FROM, 0); }
		
		/**
		 * Where.
		 *
		 * @return the terminal node
		 */
		public TerminalNode WHERE() { return getToken(CdsParser.WHERE, 0); }
		
		/**
		 * Define.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DEFINE() { return getToken(CdsParser.DEFINE, 0); }
		
		/**
		 * Union.
		 *
		 * @return the terminal node
		 */
		public TerminalNode UNION() { return getToken(CdsParser.UNION, 0); }
		
		/**
		 * Distinct.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DISTINCT() { return getToken(CdsParser.DISTINCT, 0); }
		
		/**
		 * Context.
		 *
		 * @return the terminal node
		 */
		public TerminalNode CONTEXT() { return getToken(CdsParser.CONTEXT, 0); }
		
		/**
		 * Entity.
		 *
		 * @return the terminal node
		 */
		public TerminalNode ENTITY() { return getToken(CdsParser.ENTITY, 0); }
		
		/**
		 * Type.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TYPE() { return getToken(CdsParser.TYPE, 0); }
		
		/**
		 * View.
		 *
		 * @return the terminal node
		 */
		public TerminalNode VIEW() { return getToken(CdsParser.VIEW, 0); }
		
		/**
		 * Association.
		 *
		 * @return the terminal node
		 */
		public TerminalNode ASSOCIATION() { return getToken(CdsParser.ASSOCIATION, 0); }
		
		/**
		 * To.
		 *
		 * @return the terminal node
		 */
		public TerminalNode TO() { return getToken(CdsParser.TO, 0); }
		
		/**
		 * Join types.
		 *
		 * @return the terminal node
		 */
		public TerminalNode JOIN_TYPES() { return getToken(CdsParser.JOIN_TYPES, 0); }
		
		/**
		 * Using.
		 *
		 * @return the terminal node
		 */
		public TerminalNode USING() { return getToken(CdsParser.USING, 0); }
		
		/**
		 * Default.
		 *
		 * @return the terminal node
		 */
		public TerminalNode DEFAULT() { return getToken(CdsParser.DEFAULT, 0); }
		
		/**
		 * Instantiates a new identifier context.
		 *
		 * @param parent the parent
		 * @param invokingState the invoking state
		 */
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		
		/**
		 * Gets the rule index.
		 *
		 * @return the rule index
		 */
		@Override public int getRuleIndex() { return RULE_identifier; }
		
		/**
		 * Enter rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).enterIdentifier(this);
		}
		
		/**
		 * Exit rule.
		 *
		 * @param listener the listener
		 */
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CdsListener ) ((CdsListener)listener).exitIdentifier(this);
		}
	}

	/**
	 * Identifier.
	 *
	 * @return the identifier context
	 * @throws RecognitionException the recognition exception
	 */
	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(549);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NAMESPACE) | (1L << AS) | (1L << ON) | (1L << SELECT) | (1L << FROM) | (1L << WHERE) | (1L << DEFINE) | (1L << UNION) | (1L << DISTINCT) | (1L << HANA) | (1L << JOIN_TYPES) | (1L << USING) | (1L << DEFAULT) | (1L << CONTEXT) | (1L << ENTITY) | (1L << VIEW) | (1L << TYPE) | (1L << ASSOCIATION) | (1L << TO) | (1L << ID) | (1L << STRING))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
		"\u0004\u0001Z\u0228\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0001\u0000\u0001\u0000\u0005\u0000M\b\u0000\n"+
		"\u0000\f\u0000P\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0005\u0001X\b\u0001\n\u0001\f\u0001[\t\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0005\u0002c\b\u0002\n\u0002\f\u0002f\t\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0005\u0002l\b\u0002\n\u0002\f\u0002o\t\u0002"+
		"\u0001\u0002\u0001\u0002\u0003\u0002s\b\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0005\u0003x\b\u0003\n\u0003\f\u0003{\t\u0003\u0001\u0003"+
		"\u0003\u0003~\b\u0003\u0001\u0003\u0003\u0003\u0081\b\u0003\u0001\u0003"+
		"\u0003\u0003\u0084\b\u0003\u0001\u0003\u0003\u0003\u0087\b\u0003\u0003"+
		"\u0003\u0089\b\u0003\u0001\u0004\u0005\u0004\u008c\b\u0004\n\u0004\f\u0004"+
		"\u008f\t\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0005\u0005\u0098\b\u0005\n\u0005\f\u0005\u009b"+
		"\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u00a5\b\u0005\n\u0005\f\u0005"+
		"\u00a8\t\u0005\u0001\u0005\u0001\u0005\u0003\u0005\u00ac\b\u0005\u0001"+
		"\u0006\u0005\u0006\u00af\b\u0006\n\u0006\f\u0006\u00b2\t\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006\u00b9\b\u0006"+
		"\n\u0006\f\u0006\u00bc\t\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u00c0"+
		"\b\u0006\u0001\u0007\u0005\u0007\u00c3\b\u0007\n\u0007\f\u0007\u00c6\t"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0005\u0007\u00ce\b\u0007\n\u0007\f\u0007\u00d1\t\u0007\u0001\u0007"+
		"\u0001\u0007\u0003\u0007\u00d5\b\u0007\u0001\b\u0005\b\u00d8\b\b\n\b\f"+
		"\b\u00db\t\b\u0001\b\u0003\b\u00de\b\b\u0001\b\u0001\b\u0001\b\u0001\b"+
		"\u0005\b\u00e4\b\b\n\b\f\b\u00e7\t\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0003\t\u00ee\b\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0005\n\u00f9\b\n\n\n\f\n\u00fc\t\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0005\n\u010a\b\n\n\n\f\n\u010d\t\n\u0001\n\u0001\n\u0001\n"+
		"\u0003\n\u0112\b\n\u0001\n\u0001\n\u0001\n\u0005\n\u0117\b\n\n\n\f\n\u011a"+
		"\t\n\u0003\n\u011c\b\n\u0001\u000b\u0005\u000b\u011f\b\u000b\n\u000b\f"+
		"\u000b\u0122\t\u000b\u0001\u000b\u0003\u000b\u0125\b\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u012c\b\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u0131\b\u000b\n\u000b"+
		"\f\u000b\u0134\t\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0003\f"+
		"\u013a\b\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u0010\u0003\u0010\u0143\b\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0003\u0010\u0149\b\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0005\u0010\u014f\b\u0010\n\u0010\f\u0010\u0152\t\u0010\u0001"+
		"\u0010\u0003\u0010\u0155\b\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0005\u0011\u015d\b\u0011\n\u0011\f\u0011"+
		"\u0160\t\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0005\u0012\u0167\b\u0012\n\u0012\f\u0012\u016a\t\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0005\u0013\u016f\b\u0013\n\u0013\f\u0013\u0172\t\u0013"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u0178\b\u0014"+
		"\n\u0014\f\u0014\u017b\t\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u0184\b\u0015\n"+
		"\u0015\f\u0015\u0187\t\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0005\u0016\u018e\b\u0016\n\u0016\f\u0016\u0191\t\u0016"+
		"\u0001\u0016\u0001\u0016\u0003\u0016\u0195\b\u0016\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0003\u0017\u019b\b\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0003\u0017\u01a1\b\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0003\u0017\u01a6\b\u0017\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0005\u0019\u01b4\b\u0019\n\u0019"+
		"\f\u0019\u01b7\t\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0003\u0019\u01be\b\u0019\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0003\u001a\u01c4\b\u001a\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0005\u001c\u01cd\b\u001c"+
		"\n\u001c\f\u001c\u01d0\t\u001c\u0001\u001c\u0001\u001c\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0005\u001d\u01d8\b\u001d\n\u001d\f\u001d"+
		"\u01db\t\u001d\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001f\u0003\u001f\u01e4\b\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0005\u001f\u01eb\b\u001f\n\u001f"+
		"\f\u001f\u01ee\t\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0003\u001f"+
		"\u01f3\b\u001f\u0001\u001f\u0005\u001f\u01f6\b\u001f\n\u001f\f\u001f\u01f9"+
		"\t\u001f\u0001\u001f\u0003\u001f\u01fc\b\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0003\u001f\u0202\b\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0003\u001f\u0207\b\u001f\u0003\u001f\u0209\b\u001f\u0001"+
		" \u0001 \u0001 \u0001 \u0001 \u0003 \u0210\b \u0001 \u0001 \u0001!\u0005"+
		"!\u0215\b!\n!\f!\u0218\t!\u0001\"\u0005\"\u021b\b\"\n\"\f\"\u021e\t\""+
		"\u0001#\u0005#\u0221\b#\n#\f#\u0224\t#\u0001$\u0001$\u0001$\u0004\u0168"+
		"\u0216\u021c\u0222\u0000%\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDFH\u0000\u0004"+
		"\u0002\u0000\n\f\'\'\u0004\u0000%%\'\',,5<\u0002\u0000,,;;\u0005\u0000"+
		"\u0013\u001d&&**-3;;\u0257\u0000J\u0001\u0000\u0000\u0000\u0002S\u0001"+
		"\u0000\u0000\u0000\u0004^\u0001\u0000\u0000\u0000\u0006\u0088\u0001\u0000"+
		"\u0000\u0000\b\u008d\u0001\u0000\u0000\u0000\n\u0099\u0001\u0000\u0000"+
		"\u0000\f\u00b0\u0001\u0000\u0000\u0000\u000e\u00c4\u0001\u0000\u0000\u0000"+
		"\u0010\u00d9\u0001\u0000\u0000\u0000\u0012\u00ed\u0001\u0000\u0000\u0000"+
		"\u0014\u011b\u0001\u0000\u0000\u0000\u0016\u0120\u0001\u0000\u0000\u0000"+
		"\u0018\u0139\u0001\u0000\u0000\u0000\u001a\u013b\u0001\u0000\u0000\u0000"+
		"\u001c\u013d\u0001\u0000\u0000\u0000\u001e\u013f\u0001\u0000\u0000\u0000"+
		" \u0142\u0001\u0000\u0000\u0000\"\u0158\u0001\u0000\u0000\u0000$\u0168"+
		"\u0001\u0000\u0000\u0000&\u016b\u0001\u0000\u0000\u0000(\u0173\u0001\u0000"+
		"\u0000\u0000*\u017f\u0001\u0000\u0000\u0000,\u018a\u0001\u0000\u0000\u0000"+
		".\u01a5\u0001\u0000\u0000\u00000\u01a7\u0001\u0000\u0000\u00002\u01bd"+
		"\u0001\u0000\u0000\u00004\u01c3\u0001\u0000\u0000\u00006\u01c5\u0001\u0000"+
		"\u0000\u00008\u01c8\u0001\u0000\u0000\u0000:\u01d3\u0001\u0000\u0000\u0000"+
		"<\u01de\u0001\u0000\u0000\u0000>\u01e3\u0001\u0000\u0000\u0000@\u020a"+
		"\u0001\u0000\u0000\u0000B\u0216\u0001\u0000\u0000\u0000D\u021c\u0001\u0000"+
		"\u0000\u0000F\u0222\u0001\u0000\u0000\u0000H\u0225\u0001\u0000\u0000\u0000"+
		"JN\u0003\u0002\u0001\u0000KM\u0003\u0004\u0002\u0000LK\u0001\u0000\u0000"+
		"\u0000MP\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000\u0000NO\u0001\u0000"+
		"\u0000\u0000OQ\u0001\u0000\u0000\u0000PN\u0001\u0000\u0000\u0000QR\u0003"+
		"\u0006\u0003\u0000R\u0001\u0001\u0000\u0000\u0000ST\u0005\u0013\u0000"+
		"\u0000TY\u0003H$\u0000UV\u0005\u0001\u0000\u0000VX\u0003H$\u0000WU\u0001"+
		"\u0000\u0000\u0000X[\u0001\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000"+
		"YZ\u0001\u0000\u0000\u0000Z\\\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000"+
		"\u0000\\]\u00054\u0000\u0000]\u0003\u0001\u0000\u0000\u0000^_\u0005&\u0000"+
		"\u0000_d\u0003H$\u0000`a\u0005\u0001\u0000\u0000ac\u0003H$\u0000b`\u0001"+
		"\u0000\u0000\u0000cf\u0001\u0000\u0000\u0000db\u0001\u0000\u0000\u0000"+
		"de\u0001\u0000\u0000\u0000eg\u0001\u0000\u0000\u0000fd\u0001\u0000\u0000"+
		"\u0000gh\u0005\u0002\u0000\u0000hm\u0003H$\u0000ij\u0005\u0001\u0000\u0000"+
		"jl\u0003H$\u0000ki\u0001\u0000\u0000\u0000lo\u0001\u0000\u0000\u0000m"+
		"k\u0001\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000nr\u0001\u0000\u0000"+
		"\u0000om\u0001\u0000\u0000\u0000pq\u0005\u0014\u0000\u0000qs\u0003H$\u0000"+
		"rp\u0001\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000st\u0001\u0000\u0000"+
		"\u0000tu\u00054\u0000\u0000u\u0005\u0001\u0000\u0000\u0000vx\u0003\b\u0004"+
		"\u0000wv\u0001\u0000\u0000\u0000x{\u0001\u0000\u0000\u0000yw\u0001\u0000"+
		"\u0000\u0000yz\u0001\u0000\u0000\u0000z\u0089\u0001\u0000\u0000\u0000"+
		"{y\u0001\u0000\u0000\u0000|~\u0003\n\u0005\u0000}|\u0001\u0000\u0000\u0000"+
		"}~\u0001\u0000\u0000\u0000~\u0089\u0001\u0000\u0000\u0000\u007f\u0081"+
		"\u0003\f\u0006\u0000\u0080\u007f\u0001\u0000\u0000\u0000\u0080\u0081\u0001"+
		"\u0000\u0000\u0000\u0081\u0089\u0001\u0000\u0000\u0000\u0082\u0084\u0003"+
		"\u000e\u0007\u0000\u0083\u0082\u0001\u0000\u0000\u0000\u0083\u0084\u0001"+
		"\u0000\u0000\u0000\u0084\u0089\u0001\u0000\u0000\u0000\u0085\u0087\u0003"+
		"\u0010\b\u0000\u0086\u0085\u0001\u0000\u0000\u0000\u0086\u0087\u0001\u0000"+
		"\u0000\u0000\u0087\u0089\u0001\u0000\u0000\u0000\u0088y\u0001\u0000\u0000"+
		"\u0000\u0088}\u0001\u0000\u0000\u0000\u0088\u0080\u0001\u0000\u0000\u0000"+
		"\u0088\u0083\u0001\u0000\u0000\u0000\u0088\u0086\u0001\u0000\u0000\u0000"+
		"\u0089\u0007\u0001\u0000\u0000\u0000\u008a\u008c\u00032\u0019\u0000\u008b"+
		"\u008a\u0001\u0000\u0000\u0000\u008c\u008f\u0001\u0000\u0000\u0000\u008d"+
		"\u008b\u0001\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000\u008e"+
		"\u0090\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000\u0090"+
		"\u0091\u00050\u0000\u0000\u0091\u0092\u0003H$\u0000\u0092\u0093\u0005"+
		"\u0003\u0000\u0000\u0093\u0094\u0003\u0014\n\u0000\u0094\u0095\u00054"+
		"\u0000\u0000\u0095\t\u0001\u0000\u0000\u0000\u0096\u0098\u00032\u0019"+
		"\u0000\u0097\u0096\u0001\u0000\u0000\u0000\u0098\u009b\u0001\u0000\u0000"+
		"\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u0099\u009a\u0001\u0000\u0000"+
		"\u0000\u009a\u009c\u0001\u0000\u0000\u0000\u009b\u0099\u0001\u0000\u0000"+
		"\u0000\u009c\u009d\u0005-\u0000\u0000\u009d\u009e\u0003H$\u0000\u009e"+
		"\u00a6\u0005\u0004\u0000\u0000\u009f\u00a5\u0003\n\u0005\u0000\u00a0\u00a5"+
		"\u0003\b\u0004\u0000\u00a1\u00a5\u0003\f\u0006\u0000\u00a2\u00a5\u0003"+
		"\u000e\u0007\u0000\u00a3\u00a5\u0003\u0010\b\u0000\u00a4\u009f\u0001\u0000"+
		"\u0000\u0000\u00a4\u00a0\u0001\u0000\u0000\u0000\u00a4\u00a1\u0001\u0000"+
		"\u0000\u0000\u00a4\u00a2\u0001\u0000\u0000\u0000\u00a4\u00a3\u0001\u0000"+
		"\u0000\u0000\u00a5\u00a8\u0001\u0000\u0000\u0000\u00a6\u00a4\u0001\u0000"+
		"\u0000\u0000\u00a6\u00a7\u0001\u0000\u0000\u0000\u00a7\u00a9\u0001\u0000"+
		"\u0000\u0000\u00a8\u00a6\u0001\u0000\u0000\u0000\u00a9\u00ab\u0005\u0005"+
		"\u0000\u0000\u00aa\u00ac\u00054\u0000\u0000\u00ab\u00aa\u0001\u0000\u0000"+
		"\u0000\u00ab\u00ac\u0001\u0000\u0000\u0000\u00ac\u000b\u0001\u0000\u0000"+
		"\u0000\u00ad\u00af\u00032\u0019\u0000\u00ae\u00ad\u0001\u0000\u0000\u0000"+
		"\u00af\u00b2\u0001\u0000\u0000\u0000\u00b0\u00ae\u0001\u0000\u0000\u0000"+
		"\u00b0\u00b1\u0001\u0000\u0000\u0000\u00b1\u00b3\u0001\u0000\u0000\u0000"+
		"\u00b2\u00b0\u0001\u0000\u0000\u0000\u00b3\u00b4\u00050\u0000\u0000\u00b4"+
		"\u00b5\u0003H$\u0000\u00b5\u00ba\u0005\u0004\u0000\u0000\u00b6\u00b9\u0003"+
		"\u0012\t\u0000\u00b7\u00b9\u0003 \u0010\u0000\u00b8\u00b6\u0001\u0000"+
		"\u0000\u0000\u00b8\u00b7\u0001\u0000\u0000\u0000\u00b9\u00bc\u0001\u0000"+
		"\u0000\u0000\u00ba\u00b8\u0001\u0000\u0000\u0000\u00ba\u00bb\u0001\u0000"+
		"\u0000\u0000\u00bb\u00bd\u0001\u0000\u0000\u0000\u00bc\u00ba\u0001\u0000"+
		"\u0000\u0000\u00bd\u00bf\u0005\u0005\u0000\u0000\u00be\u00c0\u00054\u0000"+
		"\u0000\u00bf\u00be\u0001\u0000\u0000\u0000\u00bf\u00c0\u0001\u0000\u0000"+
		"\u0000\u00c0\r\u0001\u0000\u0000\u0000\u00c1\u00c3\u00032\u0019\u0000"+
		"\u00c2\u00c1\u0001\u0000\u0000\u0000\u00c3\u00c6\u0001\u0000\u0000\u0000"+
		"\u00c4\u00c2\u0001\u0000\u0000\u0000\u00c4\u00c5\u0001\u0000\u0000\u0000"+
		"\u00c5\u00c7\u0001\u0000\u0000\u0000\u00c6\u00c4\u0001\u0000\u0000\u0000"+
		"\u00c7\u00c8\u0005.\u0000\u0000\u00c8\u00c9\u0003H$\u0000\u00c9\u00cf"+
		"\u0005\u0004\u0000\u0000\u00ca\u00ce\u0003\u0016\u000b\u0000\u00cb\u00ce"+
		"\u0003 \u0010\u0000\u00cc\u00ce\u0003\"\u0011\u0000\u00cd\u00ca\u0001"+
		"\u0000\u0000\u0000\u00cd\u00cb\u0001\u0000\u0000\u0000\u00cd\u00cc\u0001"+
		"\u0000\u0000\u0000\u00ce\u00d1\u0001\u0000\u0000\u0000\u00cf\u00cd\u0001"+
		"\u0000\u0000\u0000\u00cf\u00d0\u0001\u0000\u0000\u0000\u00d0\u00d2\u0001"+
		"\u0000\u0000\u0000\u00d1\u00cf\u0001\u0000\u0000\u0000\u00d2\u00d4\u0005"+
		"\u0005\u0000\u0000\u00d3\u00d5\u00054\u0000\u0000\u00d4\u00d3\u0001\u0000"+
		"\u0000\u0000\u00d4\u00d5\u0001\u0000\u0000\u0000\u00d5\u000f\u0001\u0000"+
		"\u0000\u0000\u00d6\u00d8\u00032\u0019\u0000\u00d7\u00d6\u0001\u0000\u0000"+
		"\u0000\u00d8\u00db\u0001\u0000\u0000\u0000\u00d9\u00d7\u0001\u0000\u0000"+
		"\u0000\u00d9\u00da\u0001\u0000\u0000\u0000\u00da\u00dd\u0001\u0000\u0000"+
		"\u0000\u00db\u00d9\u0001\u0000\u0000\u0000\u00dc\u00de\u0005\u0019\u0000"+
		"\u0000\u00dd\u00dc\u0001\u0000\u0000\u0000\u00dd\u00de\u0001\u0000\u0000"+
		"\u0000\u00de\u00df\u0001\u0000\u0000\u0000\u00df\u00e0\u0005/\u0000\u0000"+
		"\u00e0\u00e1\u0003H$\u0000\u00e1\u00e5\u0005\u0014\u0000\u0000\u00e2\u00e4"+
		"\u0003>\u001f\u0000\u00e3\u00e2\u0001\u0000\u0000\u0000\u00e4\u00e7\u0001"+
		"\u0000\u0000\u0000\u00e5\u00e3\u0001\u0000\u0000\u0000\u00e5\u00e6\u0001"+
		"\u0000\u0000\u0000\u00e6\u0011\u0001\u0000\u0000\u0000\u00e7\u00e5\u0001"+
		"\u0000\u0000\u0000\u00e8\u00ee\u0003H$\u0000\u00e9\u00ea\u0005\u0006\u0000"+
		"\u0000\u00ea\u00eb\u0003H$\u0000\u00eb\u00ec\u0005\u0006\u0000\u0000\u00ec"+
		"\u00ee\u0001\u0000\u0000\u0000\u00ed\u00e8\u0001\u0000\u0000\u0000\u00ed"+
		"\u00e9\u0001\u0000\u0000\u0000\u00ee\u00ef\u0001\u0000\u0000\u0000\u00ef"+
		"\u00f0\u0005\u0003\u0000\u0000\u00f0\u00f1\u0003\u0014\n\u0000\u00f1\u00f2"+
		"\u00054\u0000\u0000\u00f2\u0013\u0001\u0000\u0000\u0000\u00f3\u00f4\u0003"+
		"H$\u0000\u00f4\u00f5\u0005\u0007\u0000\u0000\u00f5\u00fa\u00055\u0000"+
		"\u0000\u00f6\u00f7\u0005\b\u0000\u0000\u00f7\u00f9\u00055\u0000\u0000"+
		"\u00f8\u00f6\u0001\u0000\u0000\u0000\u00f9\u00fc\u0001\u0000\u0000\u0000"+
		"\u00fa\u00f8\u0001\u0000\u0000\u0000\u00fa\u00fb\u0001\u0000\u0000\u0000"+
		"\u00fb\u00fd\u0001\u0000\u0000\u0000\u00fc\u00fa\u0001\u0000\u0000\u0000"+
		"\u00fd\u00fe\u0005\t\u0000\u0000\u00fe\u011c\u0001\u0000\u0000\u0000\u00ff"+
		"\u0100\u0005\u001c\u0000\u0000\u0100\u0101\u0005\u0001\u0000\u0000\u0101"+
		"\u011c\u0003H$\u0000\u0102\u0103\u0005\u001c\u0000\u0000\u0103\u0104\u0005"+
		"\u0001\u0000\u0000\u0104\u0105\u0003H$\u0000\u0105\u0106\u0005\u0007\u0000"+
		"\u0000\u0106\u010b\u00055\u0000\u0000\u0107\u0108\u0005\b\u0000\u0000"+
		"\u0108\u010a\u00055\u0000\u0000\u0109\u0107\u0001\u0000\u0000\u0000\u010a"+
		"\u010d\u0001\u0000\u0000\u0000\u010b\u0109\u0001\u0000\u0000\u0000\u010b"+
		"\u010c\u0001\u0000\u0000\u0000\u010c\u010e\u0001\u0000\u0000\u0000\u010d"+
		"\u010b\u0001\u0000\u0000\u0000\u010e\u010f\u0005\t\u0000\u0000\u010f\u011c"+
		"\u0001\u0000\u0000\u0000\u0110\u0112\u0005=\u0000\u0000\u0111\u0110\u0001"+
		"\u0000\u0000\u0000\u0111\u0112\u0001\u0000\u0000\u0000\u0112\u0113\u0001"+
		"\u0000\u0000\u0000\u0113\u0118\u0003H$\u0000\u0114\u0115\u0005\u0001\u0000"+
		"\u0000\u0115\u0117\u0003H$\u0000\u0116\u0114\u0001\u0000\u0000\u0000\u0117"+
		"\u011a\u0001\u0000\u0000\u0000\u0118\u0116\u0001\u0000\u0000\u0000\u0118"+
		"\u0119\u0001\u0000\u0000\u0000\u0119\u011c\u0001\u0000\u0000\u0000\u011a"+
		"\u0118\u0001\u0000\u0000\u0000\u011b\u00f3\u0001\u0000\u0000\u0000\u011b"+
		"\u00ff\u0001\u0000\u0000\u0000\u011b\u0102\u0001\u0000\u0000\u0000\u011b"+
		"\u0111\u0001\u0000\u0000\u0000\u011c\u0015\u0001\u0000\u0000\u0000\u011d"+
		"\u011f\u00032\u0019\u0000\u011e\u011d\u0001\u0000\u0000\u0000\u011f\u0122"+
		"\u0001\u0000\u0000\u0000\u0120\u011e\u0001\u0000\u0000\u0000\u0120\u0121"+
		"\u0001\u0000\u0000\u0000\u0121\u0124\u0001\u0000\u0000\u0000\u0122\u0120"+
		"\u0001\u0000\u0000\u0000\u0123\u0125\u0003H$\u0000\u0124\u0123\u0001\u0000"+
		"\u0000\u0000\u0124\u0125\u0001\u0000\u0000\u0000\u0125\u012b\u0001\u0000"+
		"\u0000\u0000\u0126\u012c\u0003H$\u0000\u0127\u0128\u0005\u0006\u0000\u0000"+
		"\u0128\u0129\u0003H$\u0000\u0129\u012a\u0005\u0006\u0000\u0000\u012a\u012c"+
		"\u0001\u0000\u0000\u0000\u012b\u0126\u0001\u0000\u0000\u0000\u012b\u0127"+
		"\u0001\u0000\u0000\u0000\u012c\u012d\u0001\u0000\u0000\u0000\u012d\u012e"+
		"\u0005\u0003\u0000\u0000\u012e\u0132\u0003\u0014\n\u0000\u012f\u0131\u0003"+
		"\u0018\f\u0000\u0130\u012f\u0001\u0000\u0000\u0000\u0131\u0134\u0001\u0000"+
		"\u0000\u0000\u0132\u0130\u0001\u0000\u0000\u0000\u0132\u0133\u0001\u0000"+
		"\u0000\u0000\u0133\u0135\u0001\u0000\u0000\u0000\u0134\u0132\u0001\u0000"+
		"\u0000\u0000\u0135\u0136\u00054\u0000\u0000\u0136\u0017\u0001\u0000\u0000"+
		"\u0000\u0137\u013a\u00030\u0018\u0000\u0138\u013a\u0003\u001a\r\u0000"+
		"\u0139\u0137\u0001\u0000\u0000\u0000\u0139\u0138\u0001\u0000\u0000\u0000"+
		"\u013a\u0019\u0001\u0000\u0000\u0000\u013b\u013c\u0003\u001e\u000f\u0000"+
		"\u013c\u001b\u0001\u0000\u0000\u0000\u013d\u013e\u0003\u001e\u000f\u0000"+
		"\u013e\u001d\u0001\u0000\u0000\u0000\u013f\u0140\u0007\u0000\u0000\u0000"+
		"\u0140\u001f\u0001\u0000\u0000\u0000\u0141\u0143\u0003H$\u0000\u0142\u0141"+
		"\u0001\u0000\u0000\u0000\u0142\u0143\u0001\u0000\u0000\u0000\u0143\u0144"+
		"\u0001\u0000\u0000\u0000\u0144\u0145\u0003H$\u0000\u0145\u0146\u0005\u0003"+
		"\u0000\u0000\u0146\u0148\u00051\u0000\u0000\u0147\u0149\u0003.\u0017\u0000"+
		"\u0148\u0147\u0001\u0000\u0000\u0000\u0148\u0149\u0001\u0000\u0000\u0000"+
		"\u0149\u014a\u0001\u0000\u0000\u0000\u014a\u014b\u00052\u0000\u0000\u014b"+
		"\u0150\u0003&\u0013\u0000\u014c\u014f\u0003*\u0015\u0000\u014d\u014f\u0003"+
		"(\u0014\u0000\u014e\u014c\u0001\u0000\u0000\u0000\u014e\u014d\u0001\u0000"+
		"\u0000\u0000\u014f\u0152\u0001\u0000\u0000\u0000\u0150\u014e\u0001\u0000"+
		"\u0000\u0000\u0150\u0151\u0001\u0000\u0000\u0000\u0151\u0154\u0001\u0000"+
		"\u0000\u0000\u0152\u0150\u0001\u0000\u0000\u0000\u0153\u0155\u0003\u001c"+
		"\u000e\u0000\u0154\u0153\u0001\u0000\u0000\u0000\u0154\u0155\u0001\u0000"+
		"\u0000\u0000\u0155\u0156\u0001\u0000\u0000\u0000\u0156\u0157\u00054\u0000"+
		"\u0000\u0157!\u0001\u0000\u0000\u0000\u0158\u0159\u0003H$\u0000\u0159"+
		"\u015a\u0005\u0003\u0000\u0000\u015a\u015e\u0003\u0014\n\u0000\u015b\u015d"+
		"\u0003\u0018\f\u0000\u015c\u015b\u0001\u0000\u0000\u0000\u015d\u0160\u0001"+
		"\u0000\u0000\u0000\u015e\u015c\u0001\u0000\u0000\u0000\u015e\u015f\u0001"+
		"\u0000\u0000\u0000\u015f\u0161\u0001\u0000\u0000\u0000\u0160\u015e\u0001"+
		"\u0000\u0000\u0000\u0161\u0162\u0005\r\u0000\u0000\u0162\u0163\u0003$"+
		"\u0012\u0000\u0163\u0164\u00054\u0000\u0000\u0164#\u0001\u0000\u0000\u0000"+
		"\u0165\u0167\t\u0000\u0000\u0000\u0166\u0165\u0001\u0000\u0000\u0000\u0167"+
		"\u016a\u0001\u0000\u0000\u0000\u0168\u0169\u0001\u0000\u0000\u0000\u0168"+
		"\u0166\u0001\u0000\u0000\u0000\u0169%\u0001\u0000\u0000\u0000\u016a\u0168"+
		"\u0001\u0000\u0000\u0000\u016b\u0170\u0003H$\u0000\u016c\u016d\u0005\u0001"+
		"\u0000\u0000\u016d\u016f\u0003H$\u0000\u016e\u016c\u0001\u0000\u0000\u0000"+
		"\u016f\u0172\u0001\u0000\u0000\u0000\u0170\u016e\u0001\u0000\u0000\u0000"+
		"\u0170\u0171\u0001\u0000\u0000\u0000\u0171\'\u0001\u0000\u0000\u0000\u0172"+
		"\u0170\u0001\u0000\u0000\u0000\u0173\u0174\u0005\u0015\u0000\u0000\u0174"+
		"\u0179\u0003H$\u0000\u0175\u0176\u0005\u0001\u0000\u0000\u0176\u0178\u0003"+
		"H$\u0000\u0177\u0175\u0001\u0000\u0000\u0000\u0178\u017b\u0001\u0000\u0000"+
		"\u0000\u0179\u0177\u0001\u0000\u0000\u0000\u0179\u017a\u0001\u0000\u0000"+
		"\u0000\u017a\u017c\u0001\u0000\u0000\u0000\u017b\u0179\u0001\u0000\u0000"+
		"\u0000\u017c\u017d\u0005\r\u0000\u0000\u017d\u017e\u0003H$\u0000\u017e"+
		")\u0001\u0000\u0000\u0000\u017f\u0180\u0005\u0004\u0000\u0000\u0180\u0185"+
		"\u0003,\u0016\u0000\u0181\u0182\u0005\b\u0000\u0000\u0182\u0184\u0003"+
		",\u0016\u0000\u0183\u0181\u0001\u0000\u0000\u0000\u0184\u0187\u0001\u0000"+
		"\u0000\u0000\u0185\u0183\u0001\u0000\u0000\u0000\u0185\u0186\u0001\u0000"+
		"\u0000\u0000\u0186\u0188\u0001\u0000\u0000\u0000\u0187\u0185\u0001\u0000"+
		"\u0000\u0000\u0188\u0189\u0005\u0005\u0000\u0000\u0189+\u0001\u0000\u0000"+
		"\u0000\u018a\u018f\u0003H$\u0000\u018b\u018c\u0005\u0001\u0000\u0000\u018c"+
		"\u018e\u0003H$\u0000\u018d\u018b\u0001\u0000\u0000\u0000\u018e\u0191\u0001"+
		"\u0000\u0000\u0000\u018f\u018d\u0001\u0000\u0000\u0000\u018f\u0190\u0001"+
		"\u0000\u0000\u0000\u0190\u0194\u0001\u0000\u0000\u0000\u0191\u018f\u0001"+
		"\u0000\u0000\u0000\u0192\u0193\u0005\u0014\u0000\u0000\u0193\u0195\u0003"+
		"H$\u0000\u0194\u0192\u0001\u0000\u0000\u0000\u0194\u0195\u0001\u0000\u0000"+
		"\u0000\u0195-\u0001\u0000\u0000\u0000\u0196\u0197\u0005\u000e\u0000\u0000"+
		"\u0197\u019a\u0005+\u0000\u0000\u0198\u019b\u00055\u0000\u0000\u0199\u019b"+
		"\u0005\u000f\u0000\u0000\u019a\u0198\u0001\u0000\u0000\u0000\u019a\u0199"+
		"\u0001\u0000\u0000\u0000\u019b\u019c\u0001\u0000\u0000\u0000\u019c\u01a6"+
		"\u0005\u0010\u0000\u0000\u019d\u01a0\u0005\u000e\u0000\u0000\u019e\u01a1"+
		"\u00055\u0000\u0000\u019f\u01a1\u0005\u000f\u0000\u0000\u01a0\u019e\u0001"+
		"\u0000\u0000\u0000\u01a0\u019f\u0001\u0000\u0000\u0000\u01a1\u01a2\u0001"+
		"\u0000\u0000\u0000\u01a2\u01a6\u0005\u0010\u0000\u0000\u01a3\u01a4\u0005"+
		"\u000e\u0000\u0000\u01a4\u01a6\u0005\u0010\u0000\u0000\u01a5\u0196\u0001"+
		"\u0000\u0000\u0000\u01a5\u019d\u0001\u0000\u0000\u0000\u01a5\u01a3\u0001"+
		"\u0000\u0000\u0000\u01a6/\u0001\u0000\u0000\u0000\u01a7\u01a8\u0005*\u0000"+
		"\u0000\u01a8\u01a9\u0007\u0001\u0000\u0000\u01a91\u0001\u0000\u0000\u0000"+
		"\u01aa\u01ab\u0005\u0011\u0000\u0000\u01ab\u01ac\u0003H$\u0000\u01ac\u01ad"+
		"\u0005\u0003\u0000\u0000\u01ad\u01ae\u00034\u001a\u0000\u01ae\u01be\u0001"+
		"\u0000\u0000\u0000\u01af\u01b0\u0005\u0011\u0000\u0000\u01b0\u01b5\u0003"+
		"H$\u0000\u01b1\u01b2\u0005\u0001\u0000\u0000\u01b2\u01b4\u0003H$\u0000"+
		"\u01b3\u01b1\u0001\u0000\u0000\u0000\u01b4\u01b7\u0001\u0000\u0000\u0000"+
		"\u01b5\u01b3\u0001\u0000\u0000\u0000\u01b5\u01b6\u0001\u0000\u0000\u0000"+
		"\u01b6\u01b8\u0001\u0000\u0000\u0000\u01b7\u01b5\u0001\u0000\u0000\u0000"+
		"\u01b8\u01b9\u0005\u0003\u0000\u0000\u01b9\u01ba\u00034\u001a\u0000\u01ba"+
		"\u01be\u0001\u0000\u0000\u0000\u01bb\u01bc\u0005\u0011\u0000\u0000\u01bc"+
		"\u01be\u0003H$\u0000\u01bd\u01aa\u0001\u0000\u0000\u0000\u01bd\u01af\u0001"+
		"\u0000\u0000\u0000\u01bd\u01bb\u0001\u0000\u0000\u0000\u01be3\u0001\u0000"+
		"\u0000\u0000\u01bf\u01c4\u00038\u001c\u0000\u01c0\u01c4\u00036\u001b\u0000"+
		"\u01c1\u01c4\u0003:\u001d\u0000\u01c2\u01c4\u0007\u0002\u0000\u0000\u01c3"+
		"\u01bf\u0001\u0000\u0000\u0000\u01c3\u01c0\u0001\u0000\u0000\u0000\u01c3"+
		"\u01c1\u0001\u0000\u0000\u0000\u01c3\u01c2\u0001\u0000\u0000\u0000\u01c4"+
		"5\u0001\u0000\u0000\u0000\u01c5\u01c6\u0005\u0012\u0000\u0000\u01c6\u01c7"+
		"\u0003H$\u0000\u01c77\u0001\u0000\u0000\u0000\u01c8\u01c9\u0005\u000e"+
		"\u0000\u0000\u01c9\u01ce\u00034\u001a\u0000\u01ca\u01cb\u0005\b\u0000"+
		"\u0000\u01cb\u01cd\u00034\u001a\u0000\u01cc\u01ca\u0001\u0000\u0000\u0000"+
		"\u01cd\u01d0\u0001\u0000\u0000\u0000\u01ce\u01cc\u0001\u0000\u0000\u0000"+
		"\u01ce\u01cf\u0001\u0000\u0000\u0000\u01cf\u01d1\u0001\u0000\u0000\u0000"+
		"\u01d0\u01ce\u0001\u0000\u0000\u0000\u01d1\u01d2\u0005\u0010\u0000\u0000"+
		"\u01d29\u0001\u0000\u0000\u0000\u01d3\u01d4\u0005\u0004\u0000\u0000\u01d4"+
		"\u01d9\u0003<\u001e\u0000\u01d5\u01d6\u0005\b\u0000\u0000\u01d6\u01d8"+
		"\u0003<\u001e\u0000\u01d7\u01d5\u0001\u0000\u0000\u0000\u01d8\u01db\u0001"+
		"\u0000\u0000\u0000\u01d9\u01d7\u0001\u0000\u0000\u0000\u01d9\u01da\u0001"+
		"\u0000\u0000\u0000\u01da\u01dc\u0001\u0000\u0000\u0000\u01db\u01d9\u0001"+
		"\u0000\u0000\u0000\u01dc\u01dd\u0005\u0005\u0000\u0000\u01dd;\u0001\u0000"+
		"\u0000\u0000\u01de\u01df\u0003H$\u0000\u01df\u01e0\u0005\u0003\u0000\u0000"+
		"\u01e0\u01e1\u00034\u001a\u0000\u01e1=\u0001\u0000\u0000\u0000\u01e2\u01e4"+
		"\u0005\u001a\u0000\u0000\u01e3\u01e2\u0001\u0000\u0000\u0000\u01e3\u01e4"+
		"\u0001\u0000\u0000\u0000\u01e4\u01e5\u0001\u0000\u0000\u0000\u01e5\u01e6"+
		"\u0005\u0016\u0000\u0000\u01e6\u01e7\u0005\u0017\u0000\u0000\u01e7\u01ec"+
		"\u0003H$\u0000\u01e8\u01e9\u0005\u0001\u0000\u0000\u01e9\u01eb\u0003H"+
		"$\u0000\u01ea\u01e8\u0001\u0000\u0000\u0000\u01eb\u01ee\u0001\u0000\u0000"+
		"\u0000\u01ec\u01ea\u0001\u0000\u0000\u0000\u01ec\u01ed\u0001\u0000\u0000"+
		"\u0000\u01ed\u01f2\u0001\u0000\u0000\u0000\u01ee\u01ec\u0001\u0000\u0000"+
		"\u0000\u01ef\u01f0\u0005\u0014\u0000\u0000\u01f0\u01f3\u0003H$\u0000\u01f1"+
		"\u01f3\u0003H$\u0000\u01f2\u01ef\u0001\u0000\u0000\u0000\u01f2\u01f1\u0001"+
		"\u0000\u0000\u0000\u01f2\u01f3\u0001\u0000\u0000\u0000\u01f3\u01f7\u0001"+
		"\u0000\u0000\u0000\u01f4\u01f6\u0003@ \u0000\u01f5\u01f4\u0001\u0000\u0000"+
		"\u0000\u01f6\u01f9\u0001\u0000\u0000\u0000\u01f7\u01f5\u0001\u0000\u0000"+
		"\u0000\u01f7\u01f8\u0001\u0000\u0000\u0000\u01f8\u01fb\u0001\u0000\u0000"+
		"\u0000\u01f9\u01f7\u0001\u0000\u0000\u0000\u01fa\u01fc\u0005\u001b\u0000"+
		"\u0000\u01fb\u01fa\u0001\u0000\u0000\u0000\u01fb\u01fc\u0001\u0000\u0000"+
		"\u0000\u01fc\u01fd\u0001\u0000\u0000\u0000\u01fd\u01fe\u0005\u0004\u0000"+
		"\u0000\u01fe\u01ff\u0003D\"\u0000\u01ff\u0201\u0005\u0005\u0000\u0000"+
		"\u0200\u0202\u00054\u0000\u0000\u0201\u0200\u0001\u0000\u0000\u0000\u0201"+
		"\u0202\u0001\u0000\u0000\u0000\u0202\u0208\u0001\u0000\u0000\u0000\u0203"+
		"\u0204\u0005\u0018\u0000\u0000\u0204\u0206\u0003F#\u0000\u0205\u0207\u0005"+
		"4\u0000\u0000\u0206\u0205\u0001\u0000\u0000\u0000\u0206\u0207\u0001\u0000"+
		"\u0000\u0000\u0207\u0209\u0001\u0000\u0000\u0000\u0208\u0203\u0001\u0000"+
		"\u0000\u0000\u0208\u0209\u0001\u0000\u0000\u0000\u0209?\u0001\u0000\u0000"+
		"\u0000\u020a\u020b\u0005\u001d\u0000\u0000\u020b\u020f\u0003H$\u0000\u020c"+
		"\u020d\u0005\u0014\u0000\u0000\u020d\u0210\u0003H$\u0000\u020e\u0210\u0003"+
		"H$\u0000\u020f\u020c\u0001\u0000\u0000\u0000\u020f\u020e\u0001\u0000\u0000"+
		"\u0000\u020f\u0210\u0001\u0000\u0000\u0000\u0210\u0211\u0001\u0000\u0000"+
		"\u0000\u0211\u0212\u0003B!\u0000\u0212A\u0001\u0000\u0000\u0000\u0213"+
		"\u0215\t\u0000\u0000\u0000\u0214\u0213\u0001\u0000\u0000\u0000\u0215\u0218"+
		"\u0001\u0000\u0000\u0000\u0216\u0217\u0001\u0000\u0000\u0000\u0216\u0214"+
		"\u0001\u0000\u0000\u0000\u0217C\u0001\u0000\u0000\u0000\u0218\u0216\u0001"+
		"\u0000\u0000\u0000\u0219\u021b\t\u0000\u0000\u0000\u021a\u0219\u0001\u0000"+
		"\u0000\u0000\u021b\u021e\u0001\u0000\u0000\u0000\u021c\u021d\u0001\u0000"+
		"\u0000\u0000\u021c\u021a\u0001\u0000\u0000\u0000\u021dE\u0001\u0000\u0000"+
		"\u0000\u021e\u021c\u0001\u0000\u0000\u0000\u021f\u0221\t\u0000\u0000\u0000"+
		"\u0220\u021f\u0001\u0000\u0000\u0000\u0221\u0224\u0001\u0000\u0000\u0000"+
		"\u0222\u0223\u0001\u0000\u0000\u0000\u0222\u0220\u0001\u0000\u0000\u0000"+
		"\u0223G\u0001\u0000\u0000\u0000\u0224\u0222\u0001\u0000\u0000\u0000\u0225"+
		"\u0226\u0007\u0003\u0000\u0000\u0226I\u0001\u0000\u0000\u0000FNYdmry}"+
		"\u0080\u0083\u0086\u0088\u008d\u0099\u00a4\u00a6\u00ab\u00b0\u00b8\u00ba"+
		"\u00bf\u00c4\u00cd\u00cf\u00d4\u00d9\u00dd\u00e5\u00ed\u00fa\u010b\u0111"+
		"\u0118\u011b\u0120\u0124\u012b\u0132\u0139\u0142\u0148\u014e\u0150\u0154"+
		"\u015e\u0168\u0170\u0179\u0185\u018f\u0194\u019a\u01a0\u01a5\u01b5\u01bd"+
		"\u01c3\u01ce\u01d9\u01e3\u01ec\u01f2\u01f7\u01fb\u0201\u0206\u0208\u020f"+
		"\u0216\u021c\u0222";
	
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
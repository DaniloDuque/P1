// DO NOT EDIT
// Generated by JFlex 1.9.1 http://jflex.de/
// source: lexer.flex

package org.example;

import java_cup.runtime.*;
import java.util.HashMap;
import org.example.SymbolTable;
import org.example.sym;


@SuppressWarnings("fallthrough")
public class Lexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0, 0
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\37\u0100\1\u0200\267\u0100\10\u0300\u1020\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\3\1\4\1\5\22\0\1\1"+
    "\1\0\1\6\4\0\1\7\4\0\1\10\1\11\1\12"+
    "\1\13\1\14\11\15\6\0\1\16\32\17\1\0\1\20"+
    "\2\0\1\21\1\0\1\22\1\23\1\24\1\25\1\26"+
    "\1\27\1\30\1\31\1\32\2\17\1\33\1\34\1\35"+
    "\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45"+
    "\1\46\1\47\1\50\1\51\12\0\1\3\u01a2\0\2\3"+
    "\326\0\u0100\3";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[1024];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\2\1\1\3\1\1\2\4\25\1"+
    "\1\0\1\5\1\0\1\6\47\0\1\7\2\0\1\10"+
    "\61\0\1\11\11\0\1\12\3\0\1\13\13\0\1\14"+
    "\14\0\1\15\3\0\1\16\3\0\1\14\5\0\1\17"+
    "\2\0\1\20\3\0\1\21\1\22\1\0\1\23\15\0"+
    "\1\24\1\25\1\26\5\0\1\27\1\30\11\0\1\31"+
    "\1\32\1\0\1\33\12\0\1\34\1\0\1\35\4\0"+
    "\1\36\1\37\1\40\2\0\1\41\1\0\1\42\1\43"+
    "\3\0\1\44\1\45\1\46\3\0\1\47\2\0\1\50"+
    "\2\0\1\51\1\52\6\0\1\53\1\54\2\0\1\55"+
    "\1\0\1\56\4\0\1\57\1\60\3\0\1\61\1\62"+
    "\1\0\1\63\1\64";

  private static int [] zzUnpackAction() {
    int [] result = new int[296];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\52\0\52\0\124\0\176\0\52\0\250\0\322"+
    "\0\374\0\u0126\0\u0150\0\u017a\0\u01a4\0\u01ce\0\u01f8\0\u0222"+
    "\0\u024c\0\u0276\0\u02a0\0\u02ca\0\u02f4\0\u031e\0\u0348\0\u0372"+
    "\0\u039c\0\u03c6\0\u03f0\0\u041a\0\u0444\0\u046e\0\124\0\52"+
    "\0\176\0\52\0\u0498\0\u0126\0\u04c2\0\u04ec\0\u0516\0\u0540"+
    "\0\u056a\0\u0594\0\u05be\0\u05e8\0\u0612\0\u063c\0\u0666\0\u0690"+
    "\0\u06ba\0\u06e4\0\u070e\0\u0738\0\u0762\0\u078c\0\u07b6\0\u07e0"+
    "\0\u080a\0\u0834\0\u085e\0\u0888\0\u08b2\0\u08dc\0\u0906\0\u0930"+
    "\0\u095a\0\u0984\0\u09ae\0\u09d8\0\u0a02\0\u0a2c\0\u0a56\0\u0a80"+
    "\0\u0aaa\0\u0498\0\u0ad4\0\u0afe\0\52\0\u0b28\0\u0b52\0\u0b7c"+
    "\0\u0ba6\0\u0bd0\0\u0bfa\0\u0c24\0\u0c4e\0\u0c78\0\u0ca2\0\u0ccc"+
    "\0\u0cf6\0\u0d20\0\u0d4a\0\u0d74\0\u0d9e\0\u0dc8\0\u0df2\0\u0e1c"+
    "\0\u0e46\0\u0e70\0\u0e9a\0\u0ec4\0\u0eee\0\u0f18\0\u0f42\0\u0f6c"+
    "\0\u0f96\0\u0fc0\0\u0fea\0\u1014\0\u103e\0\u1068\0\u1092\0\u10bc"+
    "\0\u10e6\0\u1110\0\u113a\0\u1164\0\u118e\0\u11b8\0\u11e2\0\u120c"+
    "\0\u1236\0\u1260\0\u128a\0\u12b4\0\u12de\0\u1308\0\52\0\u1332"+
    "\0\u135c\0\u1386\0\u13b0\0\u13da\0\u1404\0\u142e\0\u1458\0\u1482"+
    "\0\52\0\u14ac\0\u14d6\0\u1500\0\52\0\u152a\0\u1554\0\u157e"+
    "\0\u15a8\0\u15d2\0\u15fc\0\u1626\0\u1650\0\u167a\0\u16a4\0\u16ce"+
    "\0\u16f8\0\u1722\0\u174c\0\u1776\0\u17a0\0\u17ca\0\u17f4\0\u181e"+
    "\0\u1848\0\u1872\0\u189c\0\u18c6\0\u18f0\0\52\0\u191a\0\u1944"+
    "\0\u196e\0\52\0\u1998\0\u19c2\0\u19ec\0\52\0\u1a16\0\u1a40"+
    "\0\u1a6a\0\u1a94\0\u1abe\0\52\0\u1ae8\0\u1b12\0\52\0\u1b3c"+
    "\0\u1b66\0\u1b90\0\52\0\52\0\u1bba\0\52\0\u1be4\0\u1c0e"+
    "\0\u1c38\0\u1c62\0\u1c8c\0\u1cb6\0\u1ce0\0\u1d0a\0\u1d34\0\u1d5e"+
    "\0\u1d88\0\u1db2\0\u1ddc\0\52\0\52\0\52\0\u1e06\0\u1e30"+
    "\0\u1e5a\0\u1e84\0\u1eae\0\52\0\52\0\u1ed8\0\u1f02\0\u1f2c"+
    "\0\u1f56\0\u1f80\0\u1faa\0\u1fd4\0\u1ffe\0\u2028\0\52\0\52"+
    "\0\u2052\0\52\0\u207c\0\u20a6\0\u20d0\0\u20fa\0\u2124\0\u214e"+
    "\0\u2178\0\u21a2\0\u21cc\0\u21f6\0\52\0\u2220\0\52\0\u224a"+
    "\0\u2274\0\u229e\0\u22c8\0\52\0\52\0\52\0\u22f2\0\u231c"+
    "\0\52\0\u2346\0\52\0\52\0\u2370\0\u239a\0\u23c4\0\52"+
    "\0\52\0\52\0\u23ee\0\u2418\0\u2442\0\52\0\u246c\0\u2496"+
    "\0\52\0\u24c0\0\u24ea\0\52\0\52\0\u2514\0\u253e\0\u2568"+
    "\0\u2592\0\u25bc\0\u25e6\0\52\0\52\0\u2610\0\u263a\0\52"+
    "\0\u2664\0\52\0\u268e\0\u26b8\0\u26e2\0\u270c\0\52\0\52"+
    "\0\u2736\0\u2760\0\u278a\0\52\0\52\0\u27b4\0\52\0\52";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[296];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length() - 1;
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpacktrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\2\3\1\2\2\3\1\4\1\5\1\6\1\7"+
    "\2\2\1\10\1\11\1\12\1\2\1\13\1\14\1\15"+
    "\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25"+
    "\1\2\1\26\1\27\1\30\1\2\1\31\1\32\1\33"+
    "\1\34\1\35\1\36\4\2\52\0\6\37\1\40\43\37"+
    "\2\41\1\0\2\41\1\0\1\41\1\42\10\41\1\0"+
    "\31\41\14\0\1\10\1\11\46\0\1\43\51\0\1\43"+
    "\1\0\2\11\34\0\2\44\1\3\3\0\44\44\21\0"+
    "\1\45\47\0\1\46\2\0\23\46\1\47\4\46\23\0"+
    "\1\50\1\0\1\51\46\0\1\52\16\0\1\53\42\0"+
    "\1\54\3\0\1\55\5\0\1\56\51\0\1\57\40\0"+
    "\1\60\1\0\1\61\4\0\1\62\2\0\1\63\26\0"+
    "\1\64\7\0\1\65\41\0\1\66\16\0\1\67\32\0"+
    "\1\70\7\0\1\71\54\0\1\72\36\0\1\73\3\0"+
    "\1\74\3\0\1\75\41\0\1\76\13\0\1\77\52\0"+
    "\1\100\56\0\1\101\33\0\1\102\7\0\1\103\45\0"+
    "\1\104\2\0\1\105\55\0\1\106\43\0\1\107\3\0"+
    "\1\110\34\0\1\111\43\0\2\112\34\0\20\45\1\113"+
    "\1\114\30\45\14\0\2\46\1\0\1\46\1\0\1\115"+
    "\30\46\14\0\2\46\1\0\1\46\1\0\1\115\4\46"+
    "\1\116\23\46\41\0\1\117\55\0\1\120\37\0\1\121"+
    "\54\0\1\122\41\0\1\123\57\0\1\124\4\0\1\125"+
    "\47\0\1\126\40\0\1\127\52\0\1\130\65\0\1\131"+
    "\1\0\1\132\30\0\1\133\53\0\1\134\56\0\1\135"+
    "\53\0\1\136\56\0\1\137\41\0\1\140\44\0\1\141"+
    "\66\0\1\142\52\0\1\143\36\0\1\144\10\0\1\145"+
    "\43\0\1\146\53\0\1\147\55\0\1\150\3\0\1\151"+
    "\30\0\1\152\53\0\1\153\55\0\1\154\67\0\1\155"+
    "\26\0\1\156\54\0\1\157\57\0\1\160\57\0\1\161"+
    "\50\0\1\162\30\0\1\163\70\0\1\164\45\0\1\45"+
    "\3\0\1\45\23\0\1\3\52\0\2\46\1\0\1\46"+
    "\1\0\1\115\17\46\1\165\10\46\26\0\1\166\55\0"+
    "\1\167\62\0\1\170\42\0\1\171\56\0\1\172\36\0"+
    "\1\173\66\0\1\174\40\0\1\175\54\0\1\176\52\0"+
    "\1\177\54\0\1\200\42\0\1\201\11\0\1\202\51\0"+
    "\1\203\46\0\1\204\52\0\1\205\50\0\1\206\47\0"+
    "\1\207\47\0\1\210\36\0\1\211\72\0\1\212\34\0"+
    "\1\213\61\0\1\214\63\0\1\215\25\0\1\216\67\0"+
    "\1\217\50\0\1\220\42\0\1\221\50\0\1\222\55\0"+
    "\1\223\42\0\1\224\51\0\1\225\61\0\1\226\57\0"+
    "\1\227\53\0\1\230\31\0\1\231\55\0\1\232\62\0"+
    "\1\233\40\0\1\234\33\0\2\46\1\0\1\46\1\0"+
    "\1\115\1\235\27\46\24\0\1\236\1\0\1\237\12\0"+
    "\1\240\36\0\1\241\45\0\1\242\61\0\1\243\60\0"+
    "\1\244\53\0\1\245\30\0\1\246\54\0\1\247\51\0"+
    "\1\250\52\0\1\251\45\0\1\252\55\0\1\253\47\0"+
    "\1\254\55\0\1\255\47\0\1\256\51\0\1\257\45\0"+
    "\1\260\53\0\1\261\63\0\1\262\54\0\1\263\52\0"+
    "\1\264\40\0\1\265\63\0\1\266\30\0\1\267\54\0"+
    "\1\270\52\0\1\271\65\0\1\272\44\0\1\273\56\0"+
    "\1\274\42\0\1\275\44\0\1\276\46\0\1\277\63\0"+
    "\1\300\50\0\1\301\56\0\1\302\46\0\1\303\27\0"+
    "\2\46\1\0\1\46\1\0\1\115\13\46\1\304\14\46"+
    "\44\0\1\305\41\0\1\306\43\0\1\307\60\0\1\310"+
    "\65\0\1\311\42\0\1\312\31\0\1\313\51\0\1\314"+
    "\65\0\1\315\41\0\1\316\53\0\1\317\54\0\1\320"+
    "\47\0\1\321\61\0\1\322\40\0\1\323\62\0\1\324"+
    "\41\0\1\325\61\0\1\326\34\0\1\327\63\0\1\330"+
    "\45\0\1\331\41\0\1\332\52\0\1\333\61\0\1\334"+
    "\45\0\1\335\44\0\1\336\65\0\1\337\51\0\1\340"+
    "\41\0\1\341\65\0\1\342\23\0\2\46\1\0\1\46"+
    "\1\0\1\115\14\46\1\343\13\46\26\0\1\344\62\0"+
    "\1\345\42\0\1\346\64\0\1\347\30\0\1\350\72\0"+
    "\1\351\32\0\1\352\1\0\1\353\12\0\1\354\32\0"+
    "\1\355\74\0\1\356\26\0\1\357\55\0\1\360\45\0"+
    "\1\361\61\0\1\362\41\0\1\363\70\0\1\364\57\0"+
    "\1\365\27\0\1\366\70\0\1\367\27\0\1\370\65\0"+
    "\1\371\46\0\1\372\44\0\1\373\37\0\2\46\1\0"+
    "\1\46\1\0\1\374\30\46\35\0\1\375\36\0\1\376"+
    "\51\0\1\377\65\0\1\u0100\54\0\1\u0101\32\0\1\u0102"+
    "\73\0\1\u0103\41\0\1\u0104\43\0\1\u0105\51\0\1\u0106"+
    "\51\0\1\u0107\56\0\1\u0108\40\0\1\u0109\63\0\1\u010a"+
    "\43\0\1\u010b\51\0\1\u010c\56\0\1\u010d\61\0\1\u010e"+
    "\46\0\1\u010f\44\0\1\u0110\44\0\1\u0111\62\0\1\u0112"+
    "\42\0\1\u0113\56\0\1\u0114\52\0\1\u0115\36\0\1\u0116"+
    "\63\0\1\u0117\52\0\1\u0118\57\0\1\u0119\43\0\1\u011a"+
    "\50\0\1\u011b\36\0\1\u011c\51\0\1\u011d\61\0\1\u011e"+
    "\41\0\1\u011f\55\0\1\u0120\66\0\1\u0121\46\0\1\u0122"+
    "\44\0\1\u0123\54\0\1\u0124\51\0\1\u0125\57\0\1\u0126"+
    "\43\0\1\u0127\41\0\1\u0128\23\0";

  private static int [] zzUnpacktrans() {
    int [] result = new int[10206];
    int offset = 0;
    offset = zzUnpacktrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpacktrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\2\11\2\1\1\11\30\1\1\0\1\11\1\0"+
    "\1\11\47\0\1\1\2\0\1\11\61\0\1\11\11\0"+
    "\1\11\3\0\1\11\13\0\1\1\14\0\1\11\3\0"+
    "\1\11\3\0\1\11\5\0\1\11\2\0\1\11\3\0"+
    "\2\11\1\0\1\11\15\0\3\11\5\0\2\11\11\0"+
    "\2\11\1\0\1\11\12\0\1\11\1\0\1\11\4\0"+
    "\3\11\2\0\1\11\1\0\2\11\3\0\3\11\3\0"+
    "\1\11\2\0\1\11\2\0\2\11\6\0\2\11\2\0"+
    "\1\11\1\0\1\11\4\0\2\11\3\0\2\11\1\0"+
    "\2\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[296];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[Math.min(ZZ_BUFFERSIZE, zzMaxBufferLen())];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  private boolean zzEOFDone;

  /* user code: */
    // Tabla de símbolos
    SymbolTable symbolTable = new SymbolTable();

    // Método para obtener la tabla de símbolos
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** Returns the maximum size of the scanner buffer, which limits the size of tokens. */
  private int zzMaxBufferLen() {
    return Integer.MAX_VALUE;
  }

  /**  Whether the scanner buffer can grow to accommodate a larger token. */
  private boolean zzCanGrow() {
    return true;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate && zzCanGrow()) {
      /* if not, and it can grow: blow it up */
      char newBuffer[] = new char[Math.min(zzBuffer.length * 2, zzMaxBufferLen())];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      if (requested == 0) {
        throw new java.io.EOFException("Scan buffer limit reached ["+zzBuffer.length+"]");
      }
      else {
        throw new java.io.IOException(
            "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
      }
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    int initBufferSize = Math.min(ZZ_BUFFERSIZE, zzMaxBufferLen());
    if (zzBuffer.length > initBufferSize) {
      zzBuffer = new char[initBufferSize];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
  yyclose();    }
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  @Override  public java_cup.runtime.Symbol next_token() throws java.io.IOException
  {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          { return new java_cup.runtime.Symbol(sym.EOF); }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { System.err.println("Error léxico: Carácter no reconocido '" + yytext() +
                       "' en línea " + (yyline+1) + ", columna " + (yycolumn+1));
    symbolTable.addSymbol(yytext(), "ERROR", yyline, yycolumn, null);
    return new Symbol(sym.ERROR, yyline, yycolumn, yytext());
            }
          // fall through
          case 53: break;
          case 2:
            { /* No token, just consume the comment */
            }
          // fall through
          case 54: break;
          case 3:
            { symbolTable.addSymbol(yytext(), "COMA", yyline, yycolumn, null);
        return new Symbol(sym.COMA, yyline, yycolumn);
            }
          // fall through
          case 55: break;
          case 4:
            { symbolTable.addSymbol(yytext(), "LITERAL_ENTERO", yyline, yycolumn, Integer.valueOf(yytext()));
    return new Symbol(sym.LITERAL_ENTERO, yyline, yycolumn, Integer.valueOf(yytext()));
            }
          // fall through
          case 56: break;
          case 5:
            { symbolTable.addSymbol(yytext(), "LITERAL_STRING", yyline, yycolumn, yytext());
    return new Symbol(sym.LITERAL_STRING, yyline, yycolumn, yytext());
            }
          // fall through
          case 57: break;
          case 6:
            { symbolTable.addSymbol(yytext(), "LITERAL_CHAR", yyline, yycolumn, null);
   return new Symbol(sym.LITERAL_CHAR, yyline, yycolumn);
            }
          // fall through
          case 58: break;
          case 7:
            { symbolTable.addSymbol(yytext(), "LITERAL_FLOTANTE", yyline, yycolumn, Float.valueOf(yytext()));
    return new Symbol(sym.LITERAL_FLOTANTE, yyline, yycolumn, Float.valueOf(yytext()));
            }
          // fall through
          case 59: break;
          case 8:
            { symbolTable.addSymbol(yytext(), "IDENTIFICADOR", yyline, yycolumn, null);
    return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext());
            }
          // fall through
          case 60: break;
          case 9:
            { symbolTable.addSymbol(yytext(), "IF", yyline, yycolumn, null);
    return new Symbol(sym.IF, yyline, yycolumn);
            }
          // fall through
          case 61: break;
          case 10:
            { symbolTable.addSymbol(yytext(), "ELSE", yyline, yycolumn, null);
    return new Symbol(sym.ELSE, yyline, yycolumn);
            }
          // fall through
          case 62: break;
          case 11:
            { symbolTable.addSymbol(yytext(), "IGUAL", yyline, yycolumn, null);
    return new Symbol(sym.IGUAL, yyline, yycolumn);
            }
          // fall through
          case 63: break;
          case 12:
            { symbolTable.addSymbol(yytext(), "LITERAL_BOOLEANO", yyline, yycolumn, null);
    return new Symbol(sym.LITERAL_BOOLEANO, yyline, yycolumn);
            }
          // fall through
          case 64: break;
          case 13:
            { symbolTable.addSymbol(yytext(), "BREAK", yyline, yycolumn, null);
    return new Symbol(sym.BREAK, yyline, yycolumn);
            }
          // fall through
          case 65: break;
          case 14:
            { symbolTable.addSymbol(yytext(), "RETURN", yyline, yycolumn, null);
    return new Symbol(sym.RETURN, yyline, yycolumn);
            }
          // fall through
          case 66: break;
          case 15:
            { symbolTable.addSymbol(yytext(), "MODULO", yyline, yycolumn, null);
    return new Symbol(sym.MODULO, yyline, yycolumn);
            }
          // fall through
          case 67: break;
          case 16:
            { symbolTable.addSymbol(yytext(), "PRINT", yyline, yycolumn, null);
    return new Symbol(sym.PRINT, yyline, yycolumn);
            }
          // fall through
          case 68: break;
          case 17:
            { symbolTable.addSymbol(yytext(), "INCREMENTO", yyline, yycolumn, null);
    return new Symbol(sym.INCREMENTO, yyline, yycolumn);
            }
          // fall through
          case 69: break;
          case 18:
            { symbolTable.addSymbol(yytext(), "DIVISION", yyline, yycolumn, null);
    return new Symbol(sym.DIVISION, yyline, yycolumn);
            }
          // fall through
          case 70: break;
          case 19:
            { symbolTable.addSymbol(yytext(), "DOS_PUNTOS", yyline, yycolumn, null);
    return new Symbol(sym.DOS_PUNTOS, yyline, yycolumn);
            }
          // fall through
          case 71: break;
          case 20:
            { symbolTable.addSymbol(yytext(), "TIPO_STRING", yyline, yycolumn, null);
    return new Symbol(sym.TIPO_STRING, yyline, yycolumn);
            }
          // fall through
          case 72: break;
          case 21:
            { symbolTable.addSymbol(yytext(), "TIPO_CHAR", yyline, yycolumn, null);
    return new Symbol(sym.TIPO_CHAR, yyline, yycolumn);
            }
          // fall through
          case 73: break;
          case 22:
            { symbolTable.addSymbol(yytext(), "FOR", yyline, yycolumn, null);
    return new Symbol(sym.FOR, yyline, yycolumn);
            }
          // fall through
          case 74: break;
          case 23:
            { symbolTable.addSymbol(yytext(), "OR", yyline, yycolumn, null);
    return new Symbol(sym.OR, yyline, yycolumn);
            }
          // fall through
          case 75: break;
          case 24:
            { symbolTable.addSymbol(yytext(), "DECREMENTO", yyline, yycolumn, null);
    return new Symbol(sym.DECREMENTO, yyline, yycolumn);
            }
          // fall through
          case 76: break;
          case 25:
            { symbolTable.addSymbol(yytext(), "TIPO_BOOLEANO", yyline, yycolumn, null);
    return new Symbol(sym.TIPO_BOOLEANO, yyline, yycolumn);
            }
          // fall through
          case 77: break;
          case 26:
            { symbolTable.addSymbol(yytext(), "DEFAULT", yyline, yycolumn, null);
    return new Symbol(sym.DEFAULT, yyline, yycolumn);
            }
          // fall through
          case 78: break;
          case 27:
            { symbolTable.addSymbol(yytext(), "SWITCH", yyline, yycolumn, null);
    return new Symbol(sym.SWITCH, yyline, yycolumn);
            }
          // fall through
          case 79: break;
          case 28:
            { symbolTable.addSymbol(yytext(), "ASIGNACION", yyline, yycolumn, null);
    return new Symbol(sym.ASIGNACION, yyline, yycolumn);
            }
          // fall through
          case 80: break;
          case 29:
            { symbolTable.addSymbol(yytext(), "READ", yyline, yycolumn, null);
    return new Symbol(sym.READ, yyline, yycolumn);
            }
          // fall through
          case 81: break;
          case 30:
            { symbolTable.addSymbol(yytext(), "AND", yyline, yycolumn, null);
    return new Symbol(sym.AND, yyline, yycolumn);
            }
          // fall through
          case 82: break;
          case 31:
            { symbolTable.addSymbol(yytext(), "MAYOR", yyline, yycolumn, null);
    return new Symbol(sym.MAYOR, yyline, yycolumn);
            }
          // fall through
          case 83: break;
          case 32:
            { symbolTable.addSymbol(yytext(), "SUMA", yyline, yycolumn, null);
    return new Symbol(sym.SUMA, yyline, yycolumn);
            }
          // fall through
          case 84: break;
          case 33:
            { symbolTable.addSymbol(yytext(), "TIPO_ENTERO", yyline, yycolumn, null);
    return new Symbol(sym.TIPO_ENTERO, yyline, yycolumn);
            }
          // fall through
          case 85: break;
          case 34:
            { symbolTable.addSymbol(yytext(), "MAYOR_IGUAL", yyline, yycolumn, null);
    return new Symbol(sym.MAYOR_IGUAL, yyline, yycolumn);
            }
          // fall through
          case 86: break;
          case 35:
            { symbolTable.addSymbol(yytext(), "MAIN", yyline, yycolumn, null);
    return new Symbol(sym.MAIN, yyline, yycolumn);
            }
          // fall through
          case 87: break;
          case 36:
            { symbolTable.addSymbol(yytext(), "POTENCIA", yyline, yycolumn, null);
    return new Symbol(sym.POTENCIA, yyline, yycolumn);
            }
          // fall through
          case 88: break;
          case 37:
            { symbolTable.addSymbol(yytext(), "NOT", yyline, yycolumn, null);
    return new Symbol(sym.NOT, yyline, yycolumn);
            }
          // fall through
          case 89: break;
          case 38:
            { symbolTable.addSymbol(yytext(), "TIPO_FLOTANTE", yyline, yycolumn, null);
    return new Symbol(sym.TIPO_FLOTANTE, yyline, yycolumn);
            }
          // fall through
          case 90: break;
          case 39:
            { symbolTable.addSymbol(yytext(), "WHILE", yyline, yycolumn, null);
    return new Symbol(sym.WHILE, yyline, yycolumn);
            }
          // fall through
          case 91: break;
          case 40:
            { symbolTable.addSymbol(yytext(), "CASE", yyline, yycolumn, null);
    return new Symbol(sym.CASE, yyline, yycolumn);
            }
          // fall through
          case 92: break;
          case 41:
            { symbolTable.addSymbol(yytext(), "DIFERENTE", yyline, yycolumn, null);
    return new Symbol(sym.DIFERENTE, yyline, yycolumn);
            }
          // fall through
          case 93: break;
          case 42:
            { symbolTable.addSymbol(yytext(), "MENOR", yyline, yycolumn, null);
    return new Symbol(sym.MENOR, yyline, yycolumn);
            }
          // fall through
          case 94: break;
          case 43:
            { symbolTable.addSymbol(yytext(), "MENOR_IGUAL", yyline, yycolumn, null);
    return new Symbol(sym.MENOR_IGUAL, yyline, yycolumn);
            }
          // fall through
          case 95: break;
          case 44:
            { symbolTable.addSymbol(yytext(), "FIN_EXPRESION", yyline, yycolumn, null);
    return new Symbol(sym.FIN_EXPRESION, yyline, yycolumn, yytext());
            }
          // fall through
          case 96: break;
          case 45:
            { symbolTable.addSymbol(yytext(), "LLAVE_ABRE", yyline, yycolumn, null);
    return new Symbol(sym.LLAVE_ABRE, yyline, yycolumn, yytext());
            }
          // fall through
          case 97: break;
          case 46:
            { symbolTable.addSymbol(yytext(), "PARENTESIS_ABRE", yyline, yycolumn, null);
    return new Symbol(sym.PARENTESIS_ABRE, yyline, yycolumn, yytext());
            }
          // fall through
          case 98: break;
          case 47:
            { symbolTable.addSymbol(yytext(), "MULTIPLICACION", yyline, yycolumn, null);
    return new Symbol(sym.MULTIPLICACION, yyline, yycolumn);
            }
          // fall through
          case 99: break;
          case 48:
            { symbolTable.addSymbol(yytext(), "CORCHETE_ABRE", yyline, yycolumn, null);
    return new Symbol(sym.CORCHETE_ABRE, yyline, yycolumn);
            }
          // fall through
          case 100: break;
          case 49:
            { symbolTable.addSymbol(yytext(), "RESTA", yyline, yycolumn, null);
    return new Symbol(sym.RESTA, yyline, yycolumn);
            }
          // fall through
          case 101: break;
          case 50:
            { symbolTable.addSymbol(yytext(), "LLAVE_CIERRA", yyline, yycolumn, null);
    return new Symbol(sym.LLAVE_CIERRA, yyline, yycolumn, yytext());
            }
          // fall through
          case 102: break;
          case 51:
            { symbolTable.addSymbol(yytext(), "PARENTESIS_CIERRA", yyline, yycolumn, null);
    return new Symbol(sym.PARENTESIS_CIERRA, yyline, yycolumn, yytext());
            }
          // fall through
          case 103: break;
          case 52:
            { symbolTable.addSymbol(yytext(), "CORCHETE_CIERRA", yyline, yycolumn, null);
    return new Symbol(sym.CORCHETE_CIERRA, yyline, yycolumn);
            }
          // fall through
          case 104: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}

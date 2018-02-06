/*
    This file is part of lib-rANS.
    Copyright 2018 Harshad RJ

    lib-rANS is free software: you can redistribute it and/or modify
    it under the terms of the Lesser GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    lib-rANS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the Lesser GNU General Public License
    along with lib-rANS.  If not, see <http://www.gnu.org/licenses/>.
*/

package hrj.libans;

public class SymbolInfo {
  public final int freq;
  public final int start;
  public final int symbol;

  public SymbolInfo(final int freq, final int start, final int symbol) {
    super();
    this.freq = freq;
    this.start = start;
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return "SymbolInfo [freq=" + freq + ", start=" + start + ", symbol=" + symbol + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + freq;
    result = prime * result + start;
    result = prime * result + symbol;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof SymbolInfo)) {
      return false;
    }
    SymbolInfo other = (SymbolInfo) obj;
    if (freq != other.freq) {
      return false;
    }
    if (start != other.start) {
      return false;
    }
    if (symbol != other.symbol) {
      return false;
    }
    return true;
  }

}

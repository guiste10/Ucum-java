/*******************************************************************************
BSD 3-Clause License

Copyright (c) 2006+, Health Intersections Pty Ltd
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the copyright holder nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 *******************************************************************************/

package org.fhir.ucum;

import java.util.List;
import java.util.Set;


/**
 * General Ucum Service
 * 
 * UCUM functionality that is useful for applications that make use of UCUM codes
 * 
 * @author Grahame Grieve
 *
 */
public interface UcumService {

	/**
	 * provided for various utility/QA uses. Should not be used 
	 * for general use
	 * 
	 * @return the model
	 */
	public abstract UcumModel getModel();

	public class UcumVersionDetails {
		private String releaseDate;
		private String version;
		/**
		 * @param releaseDate
		 * @param version
		 */
		public UcumVersionDetails(String releaseDate, String version) {
			super();
			this.releaseDate = releaseDate;
			this.version = version;
		}
		/**
		 * @return the releaseDate
		 */
		public String getReleaseDate() {
			return releaseDate;
		}
		/**
		 * @return the version
		 */
		public String getVersion() {
			return version;
		}
		
	}
	
	/**
	 * return Ucum Identification details for the version in use
	 */
	public abstract UcumVersionDetails ucumIdentification();


	/**
	 * Check UCUM. Note that this stands as a test of the service
	 * more than UCUM itself (for version 1.7, there are no known
	 * semantic errors in UCUM). But you should always run this test at least
	 * once with the version of UCUM you are using to ensure that 
	 * the service implementation correctly understands the UCUM data
	 * to which it is bound
	 *   
	 * @return a list of internal errors in the UCUM spec.
	 * 
	 */
	public abstract List<String> validateUCUM();

	/**
	 * Search through the UCUM concepts for any concept containing matching text.
	 * Search will be limited to the kind of concept defined by kind, or all if kind
	 * is null
	 * 
	 * @param kind - can be null. scope of search
	 * @param text - required
	 * @param isRegex
	 * @return
	 */
	public abstract List<Concept> search(ConceptKind kind, String text, boolean isRegex);

	/**
	 * return a list of the defined types of units in this UCUM version
	 * 
	 * @return
	 */
	public abstract Set<String> getProperties();

	/**
	 * validate whether a unit code are valid UCUM units
	 *  
	 * @param units - the unit code to check
	 * @return nil if valid, or an error message describing the problem
	 */
	public abstract String validate(String unit);

	/**
	 * given a unit, return a formal description of what the units stand for using
	 * full names 
	 * @param units the unit code
	 * @return formal description
	 * @throws UcumException 
	 * @throws OHFException 
	 */
	public String analyse(String unit) throws UcumException ;
	
	/**
	 * validate whether a units are valid UCUM units and additionally require that the 
	 * units from a particular property
	 *  
	 * @param units - the unit code to check
	 * @return nil if valid, or an error message describing the problem
	 */
	public abstract String validateInProperty(String unit, String property);

	/**
	 * validate whether a units are valid UCUM units and additionally require that the 
	 * units match a particular base canonical unit
	 *  
	 * @param units - the unit code to check
	 * @return nil if valid, or an error message describing the problem
	 */
	public abstract String validateCanonicalUnits(String unit, String canonical);

	/**
	 * given a set of units, return their canonical form
	 * @param unit
	 * @return the canonical form
	 * @throws UcumException 
	 * @throws OHFException 
	 */
	public abstract String getCanonicalUnits(String unit) throws UcumException ;

  /**
   * given two pairs of units, return true if they sahre the same canonical base
   * 
   * @param units1
   * @param units2
   * @return
   * @throws UcumException 
   * @ 
   */
  public abstract boolean isComparable(String units1, String units2) throws UcumException ;
  
	/**
	 * for a given canonical unit, return all the defined units that have the 
	 * same canonical unit. 
	 * 
	 * @param code
	 * @return
	 * @throws UcumException 
	 * @throws OHFException
	 */
	public abstract List<DefinedUnit> getDefinedForms(String code) throws UcumException ;

	/**
	 * given a value/unit pair, return the canonical form as a value/unit pair
	 * 
	 * 1 mm -> 1e-3 m
	 * @param value
	 * @return
	 * @throws UcumException 
	 * @throws OHFException 
	 */
	public abstract Pair getCanonicalForm(Pair value) throws UcumException ;

	/**
	 * given a value and source unit, return the value in the given dest unit
	 * an exception is thrown if the conversion is not possible
	 * 
	 * @param value
	 * @param sourceUnit
	 * @param destUnit
	 * @return the value if a conversion is possible
	 * @throws UcumException 
	 * @throws OHFException
	 */
	public abstract Decimal convert(Decimal value, String sourceUnit, String destUnit) throws UcumException ;

	/**
	 * multiply two value/units pairs together and return the result in canonical units
	 * 
	 * Note: since the units returned are canonical, 
	 * @param o1
	 * @param o2
	 * @return
	 * @throws UcumException 
	 * @ 
	 */
  public abstract Pair multiply(Pair o1, Pair o2) throws UcumException ;

  /**
   * dvide two value/units pairs and return the result in canonical units
   * 
   * @param dividend
   * @param divisor
   * @return
   * @throws UcumException 
   * @ 
   */
	public abstract Pair divideBy(Pair dividend, Pair divisor) throws UcumException ;


	/**
	 * given a set of UCUM units, return a likely preferred human dense form
	 * 
	 * SI units - as is. 
	 * Other units - improved by manual fixes, or the removal of [] 
	 * 
	 * @param code
	 * @return the preferred human display form
	 */
	public abstract String getCommonDisplay(String code);



}

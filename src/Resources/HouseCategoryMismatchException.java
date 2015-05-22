/*
 * Copyright (C) 2015 Matthew Lai
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Resources;

/**
 *
 * @author Matthew Lai
 */
public class HouseCategoryMismatchException extends Exception {
    
    public HouseCategoryMismatchException () {
        super("There was an exception in creating the house price-bound categories.");
    }
    
    public HouseCategoryMismatchException (String message) {
        super(message);
    }
    
}

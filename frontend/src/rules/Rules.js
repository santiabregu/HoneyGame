// src/rules/Rules.js
import React from 'react';
import '../static/css/rules/rules.css'; // Importar el archivo CSS

function Rules() {
    return (
        <div className="rules-container">
            <div className="rules-content">
                <h2>Solo Mode:</h2>
                <p>The board starts empty. The player has a hand size of 1 and a score of 0.</p>
                <h3>Game Start:</h3>
                <ol>
                    <li>Draw a tile from the bag:</li>
                    <li>If the blank side's color is not in the corners, place it blank side up, starting from the top right and moving clockwise. Discard if the color is already on the board.</li>
                    <li>Repeat until all 6 corners are filled. The player cannot see the filled sides.</li>
                    <li>Add discarded tiles back to the bag after filling the corners.</li>
                </ol>

                <h3>Gameplay:</h3>
                <p>Draw tiles equal to your hand size (1 in the first round). Place the first tile; draw again to form a combination of 3 or more tiles. If no combination forms, place the tile blank side up and draw again.</p>
                <p>When forming combinations, flip the tiles:</p>
                <ol>
                    <li>If the filled color matches the combination, discard that tile.</li>
                    <li>If not, keep the tiles.</li>
                </ol>

                <h3>Scoring:</h3>
                <p>For combinations of 3 or more, score points based on (number of tiles - n - 1). Gain 1 point if the filled side matches.</p>
                <p>Your hand size increases by 1 for each combination made, allowing you to draw up to p tiles per turn (where p is your score).</p>

                <h3>End of Game:</h3>
                <p>The game ends in victory when no tiles remain on the board, with total points calculated as:</p>
                <p>Points = f (tiles in the bag) - h (hand size).</p>
                <p>End if no tiles remain in the bag or the board can't be cleared.</p>


                <h2>Survival Mode:</h2>
                <p>The board starts empty, and the player begins with a hand size of 1 and a score of 0.</p>
                <p>The player starts by drawing a tile from the bag:</p>
                <ol>
                    <li>If the color of the blank side of the drawn tile is not in the corners of the board, the player must place the tile blank side up, starting from the top right corner and moving clockwise.</li>
                    <li>If the color of the tile is already on the board, the tile is discarded.</li>
                    <li>This continues until all 6 corners of the board are filled.</li>
                    <li>The player cannot see the filled side of the tiles at any time.</li>
                </ol>
                <p>Once all 6 corners are filled with all available tile colors showing their blank side up, the discarded tiles from step 2.b are added back to the bag.</p>

                <h3>Game Start:</h3>
                <p>The player can choose as many tiles as they want, keeping in mind that:</p>
                <ul>
                    <li>The filled side of the tiles cannot be seen.</li>
                    <li>The bag transforms into a stack, where only the blank side of the top tile is visible.</li>
                    <li>The player must place as many tiles as they draw.</li>
                </ul>
                <p>Once the player has chosen the desired number of tiles, they seek to form combinations of the following types:</p>
                <ul>
                    <li>Line of as many tiles as desired.</li>
                    <li>Cluster of more than 4 tiles.</li>
                </ul>
                <p>Creating clusters of 3 tiles is prohibited.</p>
                <p>If the drawn tiles do not create a combination, they are placed blank side up, and the player draws more tiles as desired.</p>
                <p>If a combination is formed:</p>
                <ol>
                    <li>Flip all the tiles in the combination.</li>
                    <li>If the filled side color matches the color of the combination made with the blank sides, the player keeps those tiles.</li>
                    <li>If the colors do not match, the tiles remain blank side up.</li>
                </ol>

                <h3>Game Continuation:</h3>
                <p>The game continues until:</p>
                <ul>
                    <li><strong>Victory:</strong> When you have successfully removed all the tiles from the stack before removing any corner from the board.</li>
                    <li><strong>Defeat:</strong> If it is impossible to place more tiles on the board because it is full, or if you have removed any corners before finishing the stack.</li>
                </ul>
                <p>In both cases, your score will be calculated as follows:</p>
                <ul>
                    <li><strong>n:</strong> number of tiles removed from the board.</li>
                    <li><strong>5 * e:</strong> where <em>e</em> is the number of tiles remaining in the corners.</li>
                    <li><strong>-1 * v:</strong> where <em>v</em> is the number of tiles with the blank side up on the board.</li>
                    <li><strong>-2 * p:</strong> where <em>p</em> is the number of unplayed tiles (from the stack and filled side up).</li>
                </ul>
                <p><strong>Points = n + 5e - 1v - 2p</strong></p>

                

                <h2>Multiplayer Mode:</h2>
                <p>The board starts empty, and all players begin with a score of 0.</p>
                <p>The first player is randomly chosen to start by drawing a tile from the bag, keeping in mind that:</p>
                <ul>
                    <li>The player can see both sides of the tile.</li>
                    <li>Other players can only see the blank side of the tiles.</li>
                </ul>
                <p>When a player draws a tile, there are 2 options:</p>
                <ul>
                    <li>Place the tile on the board with the blank side up.</li>
                    <li>Keep the tile and not place it on the board.</li>
                </ul>
                <p>If a player cannot perform either action, they must pass their turn. Once placed or passed, the next player repeats the same action with the same options.</p>
                <p>A player can accumulate as many tiles as they want to form combinations but can only draw one tile each turn. If a player has accumulated tiles, they can place as many as they want on the board, following these conditions:</p>
                <ul>
                    <li>The tiles to be placed must belong to the same combination, which must be completed from that placement of tiles.</li>
                    <li>Tiles are placed one by one, and once the combination is made, no more tiles can be added to the board.</li>
                </ul>
                <p>Combinations are formed with 3 tiles in the shape of clusters, lines, or a mix of both. Upon forming a combination:</p>
                <ol>
                    <li>Flip all the tiles in the combination.</li>
                    <li>If the filled side color matches the color of the combination made with the blank sides, remove it from the board and keep that tile.</li>
                    <li>If the colors do not match, the tiles remain.</li>
                </ol>

                <h3>Scoring:</h3>
                <p>If you make combinations of 3 or more tiles, you score (number of tiles in the combination - n - 1), where n is the number of tiles in the combination.</p>
                <p>If the flipped tiles in the combination match the color of the blank sides, you score an additional point.</p>

                <h3>Game Continuation:</h3>
                <p>The game continues until the following occurs:</p>
                <ul>
                    <li><strong>Defeat:</strong> 
                        <ul>
                            <li>When no player can perform either of the two options from point 3.</li>
                            <li>If there are fewer than 3 spaces left on the board and no player can perform the two options from point 3, all players lose.</li>
                        </ul>
                    </li>
                    <li><strong>Victory:</strong> 
                        <ul>
                            <li>The player with the most points wins.</li>
                            <li>If there is a tie, the player with the most points from only combinations (not counting the tiles matching in colors) wins.</li>
                            <li>If there is still a tie, the group decides.</li>
                        </ul>
                    </li>
                </ul>


                <h2>Score Ranking:</h2>
                    <h3>Solo Mode:</h3>
                    <p>Using the scoring formula: <strong>score = f - h</strong>:</p>
                    <ul>
                        <li><strong>Bronze:</strong> If the score is less than 0.</li>
                        <li><strong>Silver:</strong> If the score is between 1 and 10.</li>
                        <li><strong>Gold:</strong> If the score is between 11 and 20.</li>
                        <li><strong>Platinum:</strong> If the score is greater than 20.</li>
                    </ul>

                    <h3>Survival Mode:</h3>
                    <p>Using the scoring formula: <strong>score = n + 5e - 1v - 2p</strong></p>
                    <ul>
                        <li><strong>Lost Game:</strong> If the score is greater than 40.</li>
                        <li><strong>Bronze:</strong> If the score is less than 55.</li>
                        <li><strong>Silver:</strong> If the score is between 55 and 65.</li>
                        <li><strong>Gold:</strong> If the score is greater than 65.</li>
                        <li><strong>Platinum:</strong> For only corners.</li>
                    </ul>

                    <h3>Multiplayer Mode:</h3>
                    <p>The winner is the player with the highest score.</p>


            </div>
        </div>

        
    );

}

export default Rules;

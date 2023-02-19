# Flag Game

This Android application is designed to help high school students learn about flags and their respective country names in a fun and enjoyable way.

## TODOs

- [ ] Create the questions bank for all countries (the initial version will have an incomplete list based on available flags)
- [ ] The questions should be of two types:
  - [ ] Type 1: Show a flag and ask the user to select the corresponding country name from a list of four possible answers.
  - [ ] Type 2: Show a country name and ask the user to select the corresponding flag from five choices presented.
- [ ] At the end of the game, present a report to the user. This report should contain an overview of the flags and country names the user correctly recognized and the correct pairs of country name and flag for the questions they answered incorrectly. This history should be saved in persistent storage and be accessible to the user when they play the game again.
- [ ] Question loading optimization: The questions presented to the user should be optimized to help students remember the country name and flag pairs. Pairs that the user has not yet mastered will be presented again in the next game round, until the user knows each pair, which will then be removed from the history bank.



NeuroElectro has ~235 entries and ModelDB has ~1800 entries,
so these two datasets are willing to give us all entry names & id.
However, we must make one HTTP request to get the detail of each entry.
Therefore, we choose to leave the "details" HashMap of the FetchedData
from these two databases empty for the initial fetch. Then, when the user
presses "Query for details" button underneath each entry, the "details"
HashMap will then be populated.

Meanwhile, NeuroMorpho has 2.6 million entries and does not work like
the two databases above. It works like a regular database, where we give
it the keywords, the starting index, and how many entries we want to get,
and it gives us all details of the entries we queried for. This works
in a page-by-page manner.

NeuroElectro and ModelDB requres the entries to be "preloaded" before
any queries, and the query logic is nearly identical. Hence, we choose
to write a "PreloadedDatabaseAccessObject" abstract class for the two
databases for code reuse.

You will see how these plays out in the implementation details.